package com.example.paymentservice.model.dto;

import com.example.paymentservice.model.entity.PaymentEntity;
import java.util.Date;

public class PaymentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long paymentAmount;
    private Date paymentDate;
    private String paymentStatus;
    private String processInstanceId;
    private String creationUser;
    private Date creationDate;

    // getters/setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    //
    public static PaymentDto toDto(PaymentEntity entity) {
        PaymentDto dto = new PaymentDto();
        if (entity == null)
            return dto;
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPaymentAmount(entity.getPaymentAmount());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setProcessInstanceId(entity.getProcessInstanceId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreationUser(entity.getCreationUser());
        dto.setPaymentDate(entity.getPaymentDate());
        return dto;
    }
}
