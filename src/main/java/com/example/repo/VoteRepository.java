package com.example.repo;

import com.example.model.User;
import com.example.model.Vote;
import com.example.util.exception.DataConflictException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v WHERE v.user = :user AND v.created >= :startOfDate AND v.created <= :date")
    List<Vote> getByUserForDate(@Param("user") User user, @Param("startOfDate") LocalDateTime startOfDate, @Param("date") LocalDateTime date);

    @Query("SELECT v FROM Vote v WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> get(int userId, int id);

    default Vote getBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }
}

