package com.example.camundaservice.service;

import com.example.camundaservice.model.dto.TaskHistoricDTO;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyHistoryService {

    private HistoryService historyService;

    public MyHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }


    public List<HistoricActivityInstance> processHistory(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();
    }

    public List<HistoricTaskInstance> taskHistory(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();
    }

    public List<TaskHistoricDTO> getCompletedTasksInProcess(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery()
                //.taskInvolvedUser(userId)
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricTaskInstanceEndTime().desc()
                .list()
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskHistoricDTO> getCompletedTasksByUser(String userId, Date startDate, Date endDate) {
        return historyService.createHistoricTaskInstanceQuery()
                //.taskAssignee(userId)
                .taskInvolvedUser(userId)
                .finished()
                //.startedAfter(startDate)
                //.startedBefore(endDate)
                .orderByHistoricTaskInstanceEndTime().desc()
                .list()
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskHistoricDTO convertToDTO(HistoricTaskInstance task) {
        TaskHistoricDTO dto = new TaskHistoricDTO();
        dto.setTaskId(task.getId());
        dto.setName(task.getName());
        dto.setProcessInstanceId(task.getProcessInstanceId());
        dto.setProcessDefinitionId(task.getProcessDefinitionId());
        dto.setStartTime(task.getStartTime());
        dto.setEndTime(task.getEndTime());
        dto.setDuration(task.getDurationInMillis());
        dto.setDeleteReason(task.getDeleteReason());
        dto.setDescription(task.getDescription());
        dto.setAssignee(task.getAssignee());
        return dto;
    }


}
