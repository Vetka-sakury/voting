package com.example.service;

import com.example.entity.Restaurant;
import com.example.repo.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repo;

    public RestaurantService(RestaurantRepository repo) {
        this.repo = repo;
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        return repo.save(restaurant);
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(int id) {
        repo.deleteById(id);
    }

    public Restaurant get(int id) {
        return repo.findById(id).orElse(null);
    }

    @Cacheable("restaurant")
    public List<Restaurant> getAll() {
        return repo.findAll();
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        repo.save(restaurant);
    }

    Restaurant getWinner() {
        return null;

    }
}
