package com.example.balance;


import com.example.balance.model.User;
import com.example.balance.model.UserDto;
import com.example.balance.repository.UserCustomRepository;
import com.example.balance.repository.UserRepository;
import com.example.balance.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCustomRepository userCustomRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUsersBalance_NullInput() {
        Map<String, Integer> newUsers = null;

        userService.createUsersBalance(newUsers);

        verify(userCustomRepository, never()).createUsers(any());
    }

    @Test
    public void testCreateUsersBalance_ValidInput() {
        Map<String, Integer> newUsers = new HashMap<>();
        newUsers.put("user1", 100);
        newUsers.put("user2", 200);

        userService.createUsersBalance(newUsers);

        verify(userCustomRepository, times(1)).createUsers(newUsers);
    }

    @Test
    public void testGetUsers() {
        User user1 = new User(1, "Alice", 100);
        User user2 = new User(2, "Bob", 200);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals(100, result.get(0).getBalance());
        assertEquals("Bob", result.get(1).getName());
        assertEquals(200, result.get(1).getBalance());
    }

    @Test
    public void testSetUsersBalance_NullInput() {
        Map<Integer, Integer> usersBalance = null;

        userService.setUsersBalance(usersBalance);

        verify(userCustomRepository, never()).updateUsersBalance(any());
    }

    @Test
    public void testSetUsersBalance_ValidInput() {
        Map<Integer, Integer> usersBalance = new HashMap<>();
        usersBalance.put(1, 150);
        usersBalance.put(2, 250);

        userService.setUsersBalance(usersBalance);

        verify(userCustomRepository, times(1)).updateUsersBalance(usersBalance);
    }

    @Test
    public void testDeleteAllUsers() {
        userService.deleteAllUsers();
        verify(userRepository, times(1)).deleteAll();
    }
}