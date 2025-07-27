package com.example.camundaservice.controller;

import com.example.camundaservice.model.CamundaRequestModel;
import com.example.camundaservice.service.MyTaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
//@CrossOrigin("http://172.16.15.72:4204")
public class TaskController {

    @Autowired
    private MyTaskService creditApprovalService;


    @PostMapping("/start")
    public ResponseEntity<?> startProcess(@RequestBody CamundaRequestModel requestModel) {
        try {
            return ResponseEntity.ok().body(creditApprovalService.startProcess(requestModel));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeTask(@RequestBody CamundaRequestModel requestModel, HttpServletRequest request) {
        try {
            creditApprovalService.checkTaskPermission(requestModel.getUserName(), requestModel.getTaskId());
            return ResponseEntity.ok().body(creditApprovalService.completeTask(requestModel));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/process-owner")
    public ResponseEntity<?> getTasks(
            @RequestParam String processInstanceId,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "50") Integer count
    ) {
        try {
            return ResponseEntity.ok().body(creditApprovalService.getProcessTasks(processInstanceId, start, count));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @GetMapping("/user-owner")
    public ResponseEntity<?> getUserTasks(
            @RequestParam String groupType,
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "50") Integer count) {
        try {
            return ResponseEntity.ok().body(creditApprovalService.getUserTasks(groupType, userId, start, count));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}