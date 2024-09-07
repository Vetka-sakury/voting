package com.example.web.vote;

import com.example.entity.Vote;
import com.example.service.VoteService;
import com.example.web.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    private final VoteService service;
    static final String REST_URL = "/votes";

    public VoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Vote update(@RequestBody Vote vote) {
        int userId = SecurityUtil.authUserId();
        return service.update(vote, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody Vote vote, @PathVariable("restaurant_id") int restaurantId) { //todo check
        int userId = SecurityUtil.authUserId();
        checkNew(vote);

        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        List<Vote> votes = service.getByUserForDate(userId, now);

        Vote created;
        if (!votes.isEmpty()) {
            if (currentTime.isAfter(LocalTime.of(11, 0))) {
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            } else {
                created = update(votes.get(0));
            }
        } else {
            created = service.create(vote, userId);
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{date}")
    public List<Vote> getResult(@PathVariable LocalDateTime date) {
        return service.getResult(date);
    }
}
