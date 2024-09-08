package com.example.service;

import com.example.entity.Restaurant;
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
public class RestaurantService {

    private final RestaurantRepository repo;
    private final DishRepository dishRepository;

    public RestaurantService(RestaurantRepository repo, DishRepository dishRepository) {
        this.repo = repo;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = "restaurantsForDate", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        return repo.save(restaurant);
    }

    @CacheEvict(value = "restaurantsForDate", allEntries = true)
    public void delete(int id) {
        repo.deleteById(id);
    }

    public Restaurant get(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return repo.findAll();
    }

    @CacheEvict(value = "restaurantsForDate", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        repo.save(restaurant);
    }

    @Cacheable("restaurantsForDate")
    public List<Restaurant> getAllActiveRestaurantsForDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime startDate = localDate.atStartOfDay();
        LocalDateTime nextDate = startDate.plusDays(1);
        return dishRepository.getAllActiveRestaurantsForDate(startDate, nextDate);
    }
}
