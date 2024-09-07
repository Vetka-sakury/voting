package com.example.repo;

import com.example.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.restaurant.id=:restaurantId")
    int deleteAllForRestaurant(@Param("restaurantId") int restaurantId);

    @Transactional
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAllByRestaurant(@Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.created >= :startOfDate AND d.created <= :date ORDER BY d.restaurant.id")
    List<Dish> getAllRestaurantMenuForDay(@Param("startOfDate")LocalDateTime startOfDate,  @Param("date")LocalDateTime date);
}