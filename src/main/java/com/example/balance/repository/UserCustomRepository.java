package com.example.balance.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class UserCustomRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Transactional
    public void createUsers(Map<String, Integer> newUsers) {
        String query = "INSERT INTO users (name, balance) VALUES (:name, :balance)";

        MapSqlParameterSource[] batch = newUsers.entrySet().stream()
                .map(pair -> new MapSqlParameterSource()
                        .addValue("name", pair.getKey())
                        .addValue("balance", pair.getValue()))
                .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(query, batch);
    }

    @Transactional
    public void updateUsersBalance(Map<Integer, Integer> userBalances) {
        String query = "UPDATE users SET balance = :balance WHERE id = :id";

        MapSqlParameterSource[] batch = userBalances.entrySet().stream()
                .map(pair -> new MapSqlParameterSource()
                        .addValue("id", pair.getKey())
                        .addValue("balance", pair.getValue())
                ).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(query, batch);
    }
}