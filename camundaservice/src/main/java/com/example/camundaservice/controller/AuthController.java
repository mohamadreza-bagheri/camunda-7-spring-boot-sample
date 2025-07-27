package com.example.camundaservice.controller;

import com.example.camundaservice.model.UserInfo;
import com.example.camundaservice.service.MyIdentityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin("http://172.16.15.72:4204")
public class AuthController {

    @Autowired
    private MyIdentityService myIdentityService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInfo userInfo, HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok().body(myIdentityService.login(userInfo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<?> syncUser(@RequestBody UserInfo userInfo, HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok().body(myIdentityService.syncUser(userInfo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("sync user failed");
        }
    }

    @GetMapping("/all-groups")
    public ResponseEntity<?> getAllGroups(HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok().body(myIdentityService.getAllGroups());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok().body(myIdentityService.getUsers());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
