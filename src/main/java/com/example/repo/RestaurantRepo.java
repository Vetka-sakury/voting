package com.example.repo;

import com.example.entity.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepo {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public RestaurantRepo(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, SimpleJdbcInsert insertUser) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = insertUser;
    }

    public boolean insertOrUpdateRestaurant(Restaurant menu) throws JsonProcessingException {
        return jdbcTemplate.update("INSERT INTO restaurant (menu) VALUES (:menu)", objectMapper.writeValueAsString(menu)) == 0;
    }

    public List<Restaurant> getAll(){
        return jdbcTemplate.queryForList("SELECT * FROM restaurant WHERE (NOW() - created) < 12 HOUR", Restaurant.class);
    }

    public boolean deleteMenu(int restaurantId){
        return jdbcTemplate.update("DELETE FROM restaurant WHERE id = :id", restaurantId) == 0;
    }

}
