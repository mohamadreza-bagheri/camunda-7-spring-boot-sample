package com.example.paymentservice.model;

import java.util.HashMap;
import java.util.Map;

public class CamundaRequestModel {
    private String processName;
    private String processInstanceId;
    private String userName;
    private String taskId;
    private Map<String, Object> variables = new HashMap<>();
    private String description;

    public CamundaRequestModel() {
    }

    public CamundaRequestModel(String processName, String processInstanceId, String userName, String taskId, Map<String, Object> variables, String description) {
        this.processName = processName;
        this.processInstanceId = processInstanceId;
        this.userName = userName;
        this.taskId = taskId;
        this.variables = variables;
        this.description = description;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
