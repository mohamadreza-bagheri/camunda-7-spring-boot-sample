package com.example.paymentservice.service;

import com.example.paymentservice.model.dto.TaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaConsumer {

    PaymentService paymentService;

    public MyKafkaConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "payment-topic", groupId = "camunda-group-2")
    public void listen(String taskMessage) {
        try {
            System.out.println("message received=" + taskMessage);
            ObjectMapper mapper = new ObjectMapper();
            TaskDTO taskDTO = mapper.readValue(taskMessage, TaskDTO.class);
            paymentService.updateStatus(taskDTO);
        } catch (JsonProcessingException e) {
            System.out.println("error when mapping message" + e.getMessage());
        } catch (Exception e) {
            System.out.println("error when reading message" + e.getMessage());
        }
    }
}
