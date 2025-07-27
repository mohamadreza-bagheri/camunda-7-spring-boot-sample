package com.example.camundaservice.model.dto;

public class ProcessInstanceDTO {
    private String processDefinitionId;
    private String businessKey;
    private String rootProcessInstanceId;
    private String caseInstanceId;
    private boolean suspended;

    public ProcessInstanceDTO() {
    }

    public ProcessInstanceDTO(String processDefinitionId, String businessKey, String rootProcessInstanceId, String caseInstanceId, boolean suspended) {
        this.processDefinitionId = processDefinitionId;
        this.businessKey = businessKey;
        this.rootProcessInstanceId = rootProcessInstanceId;
        this.caseInstanceId = caseInstanceId;
        this.suspended = suspended;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getRootProcessInstanceId() {
        return rootProcessInstanceId;
    }

    public void setRootProcessInstanceId(String rootProcessInstanceId) {
        this.rootProcessInstanceId = rootProcessInstanceId;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
}