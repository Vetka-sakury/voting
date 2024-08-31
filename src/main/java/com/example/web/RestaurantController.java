package com.example.web;

import com.example.entity.Restaurant;
import com.example.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/voting", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping(value = "/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean saveNewMenu(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant menu) {
        try {
            return service.saveMenu(menu);
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteMenu(@PathVariable int id){
        return service.deleteMenu(id);
    }

    @GetMapping(value = "/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getMenues() {
        return service.getMenues();
    }


}
