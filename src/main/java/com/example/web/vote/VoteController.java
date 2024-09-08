package com.example.web.vote;

import com.example.auth.AuthUser;
import com.example.entity.Vote;
import com.example.repo.VoteRepository;
import com.example.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    private final VoteService voteService;
    private final VoteRepository voteRepository;
    static final String REST_URL = "api/votes";

    public VoteController(VoteService service, VoteRepository voteRepository) {
        this.voteService = service;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return ResponseEntity.of(voteRepository.get(authUser.id(), id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete {} for user {}", id, authUser.id());
        Vote vote = voteRepository.getBelonged(authUser.id(), id);
        voteRepository.delete(vote);
    }

    @PostMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser, @PathVariable("restaurant_id") int restaurantId) {
        int userId = authUser.id();

        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        List<Vote> votes = voteService.getByUserForDate(userId, now);

        Vote created;
        if (!votes.isEmpty()) {
            if (currentTime.isAfter(LocalTime.of(11, 0))) {
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            } else {
                created = voteService.save(votes.get(0), votes.get(0).id());
                log.info("updated {} for user {}", created, userId);
            }
        } else {
            created = voteService.create(userId, restaurantId);
            log.info("created {} for user {}", created, userId);
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
