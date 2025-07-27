package com.example.contractservice.service;

import com.example.contractservice.model.CamundaRequestModel;
import com.example.contractservice.model.ProcessInstance;
import com.example.contractservice.model.dto.ContractDto;
import com.example.contractservice.model.dto.ContractTaskDto;
import com.example.contractservice.model.dto.TaskDTO;
import com.example.contractservice.model.entity.ContractEntity;
import com.example.contractservice.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ContractService {
    
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    @Qualifier("camunda")
    private WebClient webClient;

    @Transactional
    public ContractEntity createNew(ContractEntity contract) throws Exception {
        // Save customer credit first
        contract.setStatus("PENDING");
        contract.setCreationDate(new Date());
        contractRepository.save(contract);
        startNewProcess(contract);
        return contract;
    }
    
    public void startNewProcess(ContractEntity contract) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("contractId", contract.getId());
        CamundaRequestModel requestModel = new CamundaRequestModel();
        requestModel.setProcessName("Process_Contract");
        requestModel.setVariables(variables);

        ProcessInstance processInstance = webClient.post()
            .uri("/tasks/start")
            .bodyValue(requestModel)
            .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response
                                .bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new Exception("خطا در سرویس: " + errorBody)))
                )
            .bodyToMono(ProcessInstance.class)
            .block();

        contract.setProcessInstanceId(processInstance.getRootProcessInstanceId());
        contract.setStatus("CREATE");
        contractRepository.save(contract);
    }
    
    @Transactional
    public ContractEntity completeTask(CamundaRequestModel requestModel, String action, Long id) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("action", action);
        variables.put("contractId", id);

        requestModel.setVariables(variables);

        TaskDTO task = webClient.post()
                .uri("/tasks/complete")
                .bodyValue(requestModel)
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response
                                .bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new Exception("خطا در سرویس: " + errorBody)))
                )
                .bodyToMono(TaskDTO.class)
                .block();
        if (task == null)
            throw new Exception("invalid camunda service result!");

        return null;
    }

    public void updateStatus(TaskDTO taskDTO) {
        try {
            Integer id = (Integer) taskDTO.getVariables().get("contractId");
            String action = (String) taskDTO.getVariables().get("action");

            ContractEntity contract = contractRepository.findById(Long.valueOf(id)).orElse(null);
            if (contract != null) {
                String status = null;
                switch (taskDTO.getTaskDefinitionKey()) {
                    case "Contract_Create":
                        status = action.equals("disapprove") || action.equals("reject") ? "REJECT" : "Contract_Create";
                        break;
                    case "Contract_Approve_1":
                        status = action.equals("disapprove") ? "Contract_Create" : (action.equals("approve") ? "Contract_Approve_1" : "Rejected");
                        break;
                    case "Contract_Approve_2":
                        status = action.equals("disapprove") ? "Contract_Approve_1" : (action.equals("approve") ? "Contract_Approve_2" : "Rejected");
                        break;
                }
                contract.setStatus(status);
                contractRepository.save(contract);
            }
        } catch (Exception e) {
            // handle error
            System.out.println("خطا در بروزرسانی وضعیت قرارداد: " + e.getMessage());
        }
    }

    public List<TaskDTO> getUserTasks(String userId) {
        ResponseEntity<List<TaskDTO>> responseEntity = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tasks/user-owner")
                        .queryParam("groupType", "CONTRACT")
                        .queryParam("userId", userId)
                        .queryParam("start", 0)
                        .queryParam("count", 200)
                        .build())
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response
                                .bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new Exception("خطا در سرویس: " + errorBody)))
                )
                .toEntityList(TaskDTO.class)
                .block();
        return responseEntity.getBody();
    }

    public List<ContractTaskDto> getContractTasks(String userId) {
        List<ContractTaskDto> contractTaskDtos = new ArrayList<>();
        List<TaskDTO> taskDtos = getUserTasks(userId);
        for (TaskDTO dto: taskDtos) {
            ContractEntity contract = contractRepository.findByProcessInstanceId(dto.getProcessInstanceId());
            contractTaskDtos.add(new ContractTaskDto(dto, ContractDto.toDto(contract)));
        }
        return contractTaskDtos;
    }

}