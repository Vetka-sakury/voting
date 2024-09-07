package com.example.service;

import com.example.entity.Vote;
import com.example.repo.UserRepository;
import com.example.repo.VoteRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {

    private final VoteRepository repository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        vote.setUser(userRepository.getReferenceById(userId));
        return repository.save(vote);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Vote get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vote> getAll() {
        return repository.findAll();
    }

    public List<Vote> getAllByDate(LocalDateTime date) {
        return repository.getAllByDate(date);
    }

    public Vote update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        vote.setUser(userRepository.getReferenceById(userId));
        return repository.save(vote);
    }

    public List<Vote> getByUserForDate(int userId, LocalDateTime date) {
        LocalDateTime startOfDate = date.with(LocalTime.MIN);
        return repository.getByUserForDate(userId, startOfDate, date);
    }

    public List<Vote> getResult(LocalDateTime date) {
        LocalDateTime startOfDate = date.with(LocalTime.MIN);
        return repository.getResult(startOfDate, date);
    }
}
