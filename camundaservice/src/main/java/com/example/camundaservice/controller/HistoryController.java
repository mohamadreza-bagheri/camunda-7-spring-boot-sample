package com.example.camundaservice.controller;

import com.example.camundaservice.model.UserInfo;
import com.example.camundaservice.service.MyHistoryService;
import com.example.camundaservice.service.MyIdentityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
//@CrossOrigin("http://172.16.15.72:4204")
public class HistoryController {

    @Autowired
    private MyHistoryService historyService;


    @GetMapping("/user-tasks/{userId}")
    public ResponseEntity<?> userTasks(@PathVariable String userId, HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok().body(historyService.getCompletedTasksByUser(userId, null, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/process-tasks/{processInstanceId}")
    public ResponseEntity<?> processTasks(@PathVariable String processInstanceId, HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok().body(historyService.getCompletedTasksInProcess(processInstanceId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
