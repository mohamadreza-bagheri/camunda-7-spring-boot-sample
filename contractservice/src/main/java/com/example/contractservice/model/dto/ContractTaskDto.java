package com.example.contractservice.model.dto;

public class ContractTaskDto {
    TaskDTO task;
    ContractDto contract;

    public ContractTaskDto(TaskDTO task, ContractDto contract) {
        this.task = task;
        this.contract = contract;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    public ContractDto getContract() {
        return contract;
    }

    public void setContract(ContractDto contract) {
        this.contract = contract;
    }
}