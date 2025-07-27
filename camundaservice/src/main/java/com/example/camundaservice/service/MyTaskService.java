package com.example.camundaservice.service;

import com.example.camundaservice.model.CamundaRequestModel;
import com.example.camundaservice.model.dto.TaskDTO;
import com.example.camundaservice.model.dto.ProcessInstanceDTO;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyTaskService {

    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MyKafkaProducer producer;
    
    @Transactional
    public ProcessInstanceDTO startProcess(CamundaRequestModel requestModel) {
        // Start process instance
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(requestModel.getProcessName(), requestModel.getVariables());
        return new ProcessInstanceDTO(
                instance.getProcessDefinitionId(),
                instance.getBusinessKey(),
                instance.getRootProcessInstanceId(),
                instance.getCaseInstanceId(),
                instance.isSuspended()
        );
    }
    
    @Transactional
    public TaskDTO completeTask(CamundaRequestModel requestModel) throws Exception {
        //  Claim تسک با بررسی نتیجه
        taskService.claim(requestModel.getTaskId(), requestModel.getUserName());

        Task task = taskService.createTaskQuery().taskId(requestModel.getTaskId()).singleResult();
        if (task == null) {
            throw new Exception("Task not found with id: " + requestModel.getTaskId());
        }

        task.setDescription(requestModel.getDescription());
        taskService.saveTask(task);

        String topic = "";
        if (task.getTaskDefinitionKey().equals("Contract_Create")
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
                requestModel.getVariables()
        );
        producer.publish(topic, event);

        taskService.complete(requestModel.getTaskId(), requestModel.getVariables());

        return convertToTaskDto(task);
    }

    public void checkTaskPermission(String userName, String taskId) throws Exception {
        List<String> userGroups = identityService.createGroupQuery()
                .groupMember(userName)
                .list()
                .stream()
                .map(Group::getId)
                .collect(Collectors.toList());

        List<Task> tasks = taskService.createTaskQuery()
            .taskId(taskId)
            .or()
                .taskAssignee(userName)
                .taskCandidateUser(userName)
                .taskCandidateGroupIn(userGroups)
            .endOr()
            .list();

        if (tasks.isEmpty()) {
            throw new Exception("you have not permission to complete task!");
        }
    }

    public List<TaskDTO> getProcessTasks(String processInstanceId, int start, int count) {
        return taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .listPage(start, count)
            .stream()
            .map(this::convertToTaskDto)
            .collect(Collectors.toList());
    }

    public List<TaskDTO> getUserTasks(String groupType, String userId, int start, int count) {
        List<String> userGroups = new ArrayList<>();
        if (groupType != null && !groupType.isEmpty()) {
            userGroups = identityService.createGroupQuery()
                    .groupType(groupType)
                    .groupMember(userId)
                    .listPage(start, count)
                    .stream()
                    .map(Group::getId)
                    .collect(Collectors.toList());
        } else {
            userGroups = identityService.createGroupQuery()
                    .groupMember(userId)
                    .listPage(start, count)
                    .stream()
                    .map(Group::getId)
                    .collect(Collectors.toList());
        }

        return taskService
                .createTaskQuery()
                .or()
                .taskAssignee(userId)
                .taskCandidateUser(userId)
                .taskCandidateGroupIn(userGroups)
                .endOr()
                .orderByTaskCreateTime().desc()
                .list()
                .stream().map(this::convertToTaskDto)
                .collect(Collectors.toList());
    }

    private void addAuthentication(String groupId) {
        List<Authorization> authorizations = authorizationService
                .createAuthorizationQuery()
                .groupIdIn(groupId)
                .list();

        if (authorizations.isEmpty()) {
            Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
            auth.setGroupId("employee");
            auth.setResource(Resources.TASK);
            auth.setResourceId("*");
            auth.addPermission(Permissions.READ);
            auth.addPermission(Permissions.UPDATE);
            authorizationService.saveAuthorization(auth);
        }
    }

    private TaskDTO convertToTaskDto(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getOwner(),
                task.getAssignee(),
                task.getProcessInstanceId(),
                task.getTaskDefinitionKey(),
                task.getCreateTime(),
                new HashMap<>()
        );
    }
}