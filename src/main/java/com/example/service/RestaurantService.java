package com.example.service;

import com.example.entity.Restaurant;
import com.example.entity.User;
import com.example.repo.RestaurantRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepo repo;

    public RestaurantService(RestaurantRepo repo) {
        this.repo = repo;
    }

    public boolean saveMenu(Restaurant menu) throws JsonProcessingException {
        return repo.insertOrUpdateRestaurant(menu);
    }

    public boolean deleteMenu(int id){
        return repo.deleteMenu(id);
    }

    void vote(Restaurant restaurant, User user) {

    }

    public List<Restaurant> getMenues(){
        return repo.getAll();
    }

    Restaurant getWinner() {
        return null;

    }
}
