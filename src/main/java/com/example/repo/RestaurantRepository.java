package com.example.repo;

import com.example.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    void deleteById(Integer integer);

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

}
