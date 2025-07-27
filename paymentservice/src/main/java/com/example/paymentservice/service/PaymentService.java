package com.example.paymentservice.service;


import com.example.paymentservice.model.CamundaRequestModel;
import com.example.paymentservice.model.dto.ContractDto;
import com.example.paymentservice.model.dto.PaymentDto;
import com.example.paymentservice.model.dto.PaymentTaskDto;
import com.example.paymentservice.model.dto.TaskDTO;
import com.example.paymentservice.model.entity.PaymentEntity;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    @Qualifier("camunda")
    private WebClient camundaClient;

    @Autowired
    @Qualifier("contract")
    private WebClient contractClient;

    @Transactional
    public PaymentEntity createNew(String processInstanceId) throws Exception {
        // Save customer credit first
        ContractDto contractDto = getContractByProcessInstanceId(processInstanceId);
        PaymentEntity payment = new PaymentEntity();
        payment.setFirstName(contractDto.getFirstName());
        payment.setLastName(contractDto.getLastName());
        payment.setPaymentAmount(contractDto.getContractAmount());
        payment.setContractNo(contractDto.getId());
        payment.setProcessInstanceId(processInstanceId);
        payment.setPaymentStatus("PENDING");
        payment.setCreationDate(new Date());
        paymentRepository.save(payment);
        return payment;
    }

    @Transactional
    public PaymentEntity completeTask(CamundaRequestModel requestModel, String action, Long id) throws Exception {
        PaymentEntity payment;
        if (id == null || id == 0L) {
            payment = createNew(requestModel.getProcessInstanceId());
        } else {
            payment = paymentRepository.findById(id).orElse(null);
        }
        if (payment == null)
            throw new Exception("payment data not exists!");

        Map<String, Object> variables = new HashMap<>();
        variables.put("action", action);
        variables.put("paymentId", payment.getId());

        requestModel.setVariables(variables);

        TaskDTO task = camundaClient.post()
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

        return payment;
    }

    public void updateStatus(TaskDTO taskDTO) {
        try {
            Integer id = (Integer) taskDTO.getVariables().get("paymentId");
            String action = (String) taskDTO.getVariables().get("action");

            PaymentEntity payment = paymentRepository.findById(Long.valueOf(id)).orElse(null);
            if (payment != null) {
                String status = null;
                switch (taskDTO.getTaskDefinitionKey()) {
                    case "Payment_Approve_1":
                        payment.setPaymentStatus( action.equals("approve") ? "Payment_Approve_1" : "payment_Approve_1" );
                        break;
                    case "Payment_Approve_2":
                        payment.setPaymentStatus( action.equals("approve") ? "Payment_Approve_2" : "Payment_Approve_1" );
                        payment.setPaymentDate(new Date());
                        break;
                    case "Payment":
                        payment.setPaymentStatus( "Payment" );
                        break;
                }
                paymentRepository.save(payment);
            }
        } catch (Exception e) {
            // handle error
            System.out.println("خطا در بروزرسانی وضعیت پرداخت: " + e.getMessage());
        }
    }

    public List<TaskDTO> getUserTasks(String userId) {
        ResponseEntity<List<TaskDTO>> responseEntity = camundaClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tasks/user-owner")
                        .queryParam("groupType", "PAYMENT")
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
    
    public ContractDto getContractByProcessInstanceId(String processInstanceId) throws Exception {
        ContractDto contractDto = contractClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/contracts/getByProcess")
                        .queryParam("processInstanceId", processInstanceId)
                        .build())
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        response -> response
                                .bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new Exception("خطا در سرویس: " + errorBody)))
                )
                .bodyToMono(ContractDto.class)
                .block();
        if (contractDto == null || contractDto.getId() == null)
            throw new Exception("invalid contract result!");

        return contractDto;
    }

    public List<PaymentTaskDto> getPaymentTasks(String userId) {
        List<PaymentTaskDto> paymentTaskDtos = new ArrayList<>();
        List<TaskDTO> taskDtos = getUserTasks(userId);
        for (TaskDTO dto: taskDtos) {
            PaymentEntity payment = paymentRepository.findByProcessInstanceId(dto.getProcessInstanceId());
            paymentTaskDtos.add(new PaymentTaskDto(dto, PaymentDto.toDto(payment)));
        }
        return paymentTaskDtos;
    }

}