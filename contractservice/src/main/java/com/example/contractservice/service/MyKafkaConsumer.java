package com.example.contractservice.service;

import com.example.contractservice.model.dto.TaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaConsumer {

    ContractService contractService;

    public MyKafkaConsumer(ContractService contractService) {
        this.contractService = contractService;
    }

    @KafkaListener(topics = "contract-topic", groupId = "camunda-group-1")
    public void listen(String taskMessage) {
        try {
            System.out.println("message received=" + taskMessage);
            ObjectMapper mapper = new ObjectMapper();
            TaskDTO taskDTO = mapper.readValue(taskMessage, TaskDTO.class);
            contractService.updateStatus(taskDTO);
        } catch (JsonProcessingException e) {
            System.out.println("error when mapping message" + e.getMessage());
        } catch (Exception e) {
            System.out.println("error when reading message" + e.getMessage());
        }
    }
}
