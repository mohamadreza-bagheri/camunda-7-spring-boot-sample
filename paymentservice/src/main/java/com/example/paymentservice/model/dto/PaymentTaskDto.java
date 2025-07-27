package com.example.paymentservice.model.dto;

public class PaymentTaskDto {
    TaskDTO task;
    PaymentDto payment;

    public PaymentTaskDto(TaskDTO task, PaymentDto payment) {
        this.task = task;
        this.payment = payment;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }
}