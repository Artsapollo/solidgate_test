package com.example.balance.controller;

import com.example.balance.model.UserDto;
import com.example.balance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("create-users")
    public ResponseEntity<Void> createUsers(@RequestBody Map<String, Integer> request) {
        service.createUsersBalance(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = service.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("set-users-balance")
    public ResponseEntity<Void> setUsersBalance(@RequestBody Map<Integer, Integer> request) {
        service.setUsersBalance(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete-users")
    public ResponseEntity<Void> deleteUsers() {
        service.deleteAllUsers();
        return ResponseEntity.ok().build();
    }

}