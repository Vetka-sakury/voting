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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.util.validation.ValidationUtil.assureIdConsistent;
import static com.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishController {

    private final DishService dishService;
    static final String REST_URL = "/api";

    public DishController(DishService service) {
        this.dishService = service;
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        return ResponseEntity.ok(dishService.get(id));
    }

    @DeleteMapping("/dishes/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        dishService.delete(id);
    }

    @PutMapping(value = "/restaurant/{restaurant_id}/dishes/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("restaurant_id") int restaurantId, @PathVariable("id") int dishId) {
        assureIdConsistent(dish, dishId);
        dishService.update(dish, restaurantId);
    }

    @PostMapping(value = "/restaurant/{restaurant_id}/dishes/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody DishTo dishTo, @PathVariable("restaurant_id") int restaurantId) {
        checkNew(dishTo);
        Dish created = dishService.create(DishUtil.createNewFromTo(dishTo), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/restaurant/{restaurant_id}/dishes/{date}")
    public List<Dish> getRestaurantMenuForDay(@PathVariable("restaurant_id") int restaurantId, @PathVariable("date") String date) {
        return dishService.getRestaurantMenuForDay(restaurantId, date);
    }

    @GetMapping("/dishes/admin")
    public List<Dish> getAllDishes() {
        return dishService.getAll();
    }
}
