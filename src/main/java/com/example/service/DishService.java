package com.example.service;

import com.example.entity.Dish;
import com.example.repo.DishRepository;
import com.example.repo.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = {"dishesForDateByRestaurant", "restaurantsForDate"}, allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(dish);
    }

    @CacheEvict(value = {"dishesForDateByRestaurant", "restaurantsForDate"}, allEntries = true)
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Dish get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }

    @CacheEvict(value = {"dishesForDateByRestaurant", "restaurantsForDate"}, allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        repository.save(dish);
    }

    @Cacheable("dishesForDateByRestaurant")
    public List<Dish> getRestaurantMenuForDay(int restaurantId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime startDate = localDate.atStartOfDay();
        LocalDateTime nextDate = startDate.plusDays(1);
        return repository.getRestaurantMenuForDay(restaurantId, startDate, nextDate);
    }
}