package com.example.repo;

import com.example.model.Dish;
import com.example.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.created >= :startOfDate AND d.created < :date AND d.restaurant.id=:restaurantId")
    List<Dish> getRestaurantMenuForDay(@Param("restaurantId") int restaurantId, @Param("startOfDate") LocalDateTime startOfDate, @Param("date") LocalDateTime date);

    @Query("SELECT DISTINCT d.restaurant FROM Dish d WHERE d.created >= :startOfDate AND d.created < :date")
    List<Restaurant> getAllActiveRestaurantsForDate(@Param("startOfDate") LocalDateTime startOfDate, @Param("date") LocalDateTime date);
}