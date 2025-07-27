package com.example.contractservice.controller;

import com.example.contractservice.model.CamundaRequestModel;
import com.example.contractservice.model.dto.ContractDto;
import com.example.contractservice.model.entity.ContractEntity;
import com.example.contractservice.repository.ContractRepository;
import com.example.contractservice.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
@CrossOrigin("http://172.16.15.72:4204")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ContractEntity contract) {
        try {
            return ResponseEntity.ok().body(contractService.createNew(contract));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approve(
            @RequestParam String action,
            @RequestParam Long id,
            @RequestBody CamundaRequestModel approvalModel
    ) {
        try {
            return ResponseEntity.ok().body(contractService.completeTask(approvalModel, action, id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(contractRepository.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(contractRepository.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @GetMapping("/tasks/{userId}")
    public ResponseEntity<?> getUserTasks(@PathVariable String userId) {
        try {
            return ResponseEntity.ok().body(contractService.getContractTasks(userId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/getByProcess")
    public ResponseEntity<?> getByProcess(@RequestParam String processInstanceId) {
        try {
            ContractEntity entity = contractRepository.findByProcessInstanceId(processInstanceId);
            ContractDto contractDto = ContractDto.toDto(entity);
            return ResponseEntity.ok().body(contractDto);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }



}