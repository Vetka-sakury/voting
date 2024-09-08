package com.example.service;

import com.example.entity.Dish;
import com.example.repo.DishRepository;
import com.example.repo.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = "dish", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(dish);
    }

    @CacheEvict(value = "dish", allEntries = true)
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Dish get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }

    @Cacheable("dishByRestaurant")//todo check
    public List<Dish> getAllByRestaurant(int restaurantId) {
        return repository.getAllByRestaurant(restaurantId);
    }

    @CacheEvict(value = "dish", allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        repository.save(dish);
    }

    @Cacheable("dish")
    public List<Dish> getAllRestaurantMenuForDay(LocalDateTime date) {
        LocalDateTime startOfDate = date.with(LocalTime.MIN);
        return repository.getAllRestaurantMenuForDay(startOfDate, date);
    }
}