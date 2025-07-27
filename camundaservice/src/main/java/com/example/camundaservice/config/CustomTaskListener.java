package com.example.camundaservice.config;

import com.example.camundaservice.model.TaskEvent;
import com.example.camundaservice.model.dto.TaskDTO;
import com.example.camundaservice.service.MyKafkaProducer;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CustomTaskListener implements TaskListener {
    private final MyKafkaProducer eventPublisher; // every messaging service

    public CustomTaskListener(MyKafkaProducer eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void notify(DelegateTask task) {
        String topic = "";
        if (task.getName().equals("Contract_Create")
                || task.getName().equals("Contract_Approve_1")
                || task.getName().equals("Contract_Approve_2")
        )
            topic = "contract-topic";
        else
            topic = "payment-topic";

        TaskDTO event = new TaskDTO(
                task.getId(),
                task.getName(),
                task.getOwner(),
                task.getAssignee(),
                task.getProcessInstanceId(),
                task.getTaskDefinitionKey(),
                task.getCreateTime(),
                task.getVariables()
        );
        eventPublisher.publish(topic, event); // send event by message
    }
}