package com.example.service;

import com.example.entity.Vote;
import com.example.repo.RestaurantRepository;
import com.example.repo.UserRepository;
import com.example.repo.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository repository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote create(int userId, int restaurantId) {
        Vote vote = new Vote(null);
        vote.setUser(userRepository.getReferenceById(userId));
        vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(vote);
    }

    public Optional<Vote> get(int userId, int id) {
        return repository.get(userId, id);
    }

    public Vote save(Vote vote, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(vote);
    }

    public List<Vote> getByUserForDate(int userId, LocalDateTime date) {
        LocalDateTime startOfDate = date.with(LocalTime.MIN);
        return repository.getByUserForDate(userId, startOfDate, date);
    }
}
