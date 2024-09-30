package com.example.balance;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.balance.repository.UserCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.*;

public class UserRepositoryTest {
    @InjectMocks
    private UserCustomRepository userCustomRepository;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUsers_ValidInput() {
        Map<String, Integer> newUsers = new HashMap<>();
        newUsers.put("user1", 100);
        newUsers.put("user2", 200);

        userCustomRepository.createUsers(newUsers);

        MapSqlParameterSource[] expectedBatch = newUsers.entrySet().stream()
                .map(pair -> new MapSqlParameterSource()
                        .addValue("name", pair.getKey())
                        .addValue("balance", pair.getValue()))
                .toArray(MapSqlParameterSource[]::new);

        verify(jdbcTemplate, times(1)).batchUpdate(anyString(), eq(expectedBatch));
    }

    @Test
    public void testUpdateUsersBalance_ValidInput() {
        Map<Integer, Integer> userBalances = new HashMap<>();
        userBalances.put(1, 150);
        userBalances.put(2, 250);

        userCustomRepository.updateUsersBalance(userBalances);

        MapSqlParameterSource[] expectedBatch = userBalances.entrySet().stream()
                .map(pair -> new MapSqlParameterSource()
                        .addValue("id", pair.getKey())
                        .addValue("balance", pair.getValue()))
                .toArray(MapSqlParameterSource[]::new);

        verify(jdbcTemplate, times(1)).batchUpdate(anyString(), eq(expectedBatch));
    }
}