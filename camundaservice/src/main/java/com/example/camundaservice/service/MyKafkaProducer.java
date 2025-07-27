package com.example.camundaservice.service;

import com.example.camundaservice.model.TaskEvent;
import com.example.camundaservice.model.dto.TaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaProducer {
    
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MyKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, TaskDTO message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String taskAsString = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(topic, taskAsString);
            System.out.println("sending message to " + topic + " message= " + message);
        } catch (JsonProcessingException e) {
            System.out.println("error when mapping message" + e.getMessage());
        } catch (Exception e) {
            System.out.println("error when sending message" + e.getMessage());
        }
    }
}
