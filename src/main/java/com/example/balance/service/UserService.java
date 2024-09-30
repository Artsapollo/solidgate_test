package com.example.balance.service;

import com.example.balance.model.User;
import com.example.balance.model.UserDto;
import com.example.balance.repository.UserCustomRepository;
import com.example.balance.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserCustomRepository customRepository;

    public void createUsersBalance(Map<String, Integer> newUsers) {
        if (null == newUsers) {
            log.error("No users to create");
            return;
        }
        customRepository.createUsers(newUsers);
    }

    public List<UserDto> getUsers() {
        List<User> allUsers = repository.findAll();
        return allUsers.stream()
                .map(u -> new UserDto(u.getID(), u.getName(), u.getBalance()))
                .toList();
    }

    public void setUsersBalance(Map<Integer, Integer> usersBalance) {
        if (null == usersBalance) {
            log.error("Request data is empty");
            return;
        }
        customRepository.updateUsersBalance(usersBalance);
    }

    public void deleteAllUsers() {
        repository.deleteAll();
    }
}
