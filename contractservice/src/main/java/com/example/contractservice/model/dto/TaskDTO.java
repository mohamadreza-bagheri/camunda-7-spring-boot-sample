package com.example.contractservice.model.dto;

import java.util.Date;
import java.util.Map;

public class TaskDTO {
    private String id;
    private String name;
    private String owner;
    private String assignee;
    private String processInstanceId;
    private String taskDefinitionKey;
    private Date createTime;
    private Map<String, Object> variables;

    public TaskDTO() {
    }

    public TaskDTO(String id, String name, String owner, String assignee, String processInstanceId, String taskDefinitionKey, Date createTime, Map<String, Object> variables) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.assignee = assignee;
        this.processInstanceId = processInstanceId;
        this.taskDefinitionKey = taskDefinitionKey;
        this.createTime = createTime;
        this.variables = variables;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}