package com.example.repo;

import com.example.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
}