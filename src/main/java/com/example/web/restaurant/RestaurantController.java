package com.example.web.restaurant;

import com.example.entity.Restaurant;
import com.example.service.RestaurantService;
import com.example.to.RestaurantTo;
import com.example.util.RestaurantUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;
    static final String REST_URL = "api/restaurants";

    public RestaurantController(RestaurantService service) {
        this.restaurantService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.ok(restaurantService.get(id));
    }

    @GetMapping("/admin")
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/active/{date}")
    public List<Restaurant> getAllForDate(@PathVariable("date") String date) {
        return restaurantService.getAllActiveRestaurantsForDate(date);
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }

    @PutMapping(value = "/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        Restaurant created = restaurantService.create(RestaurantUtil.createNewFromTo(restaurantTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
