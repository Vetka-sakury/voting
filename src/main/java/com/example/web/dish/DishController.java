package com.example.web.dish;

import com.example.entity.Dish;
import com.example.service.DishService;
import com.example.to.DishTo;
import com.example.util.DishUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishController {

    private final DishService dishService;
    static final String REST_URL = "/api/dishes";

    public DishController(DishService service) {
        this.dishService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        return ResponseEntity.ok(dishService.get(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable int id) {
        dishService.delete(id);
    }

    @PutMapping(value = "/restaurant/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@RequestBody Dish dish, @PathVariable("restaurant_id") int restaurantId) {
        dishService.update(dish, restaurantId);
    }

    @PostMapping(value = "/restaurant/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Dish> create(@RequestBody DishTo dishTo, @PathVariable("restaurant_id") int restaurantId) {
        checkNew(dishTo);
        Dish created = dishService.create(DishUtil.createNewFromTo(dishTo), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @GetMapping("/{date}")
//    public List<Dish> getAllRestaurantMenuForDay(@PathVariable LocalDateTime date) {
//        return service.getAllRestaurantMenuForDay(date);
//    }
}
