package com.example.contractservice.model.dto;

import com.example.contractservice.model.entity.ContractEntity;
import jakarta.persistence.*;

import java.util.Date;


public class ContractDto {
    private Long id;
    private Long contractGroupId;
    private String firstName;
    private String lastName;
    private Long contractAmount;
    private String status;
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

    public Long getContractGroupId() {
        return contractGroupId;
    }

    public void setContractGroupId(Long contractGroupId) {
        this.contractGroupId = contractGroupId;
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

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public static ContractDto toDto(ContractEntity entity) {
        ContractDto dto = new ContractDto();
        if (entity == null)
            return dto;
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setContractAmount(entity.getContractAmount());
        dto.setStatus(entity.getStatus());
        dto.setProcessInstanceId(entity.getProcessInstanceId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setCreationUser(entity.getCreationUser());
        return dto;
    }
}
