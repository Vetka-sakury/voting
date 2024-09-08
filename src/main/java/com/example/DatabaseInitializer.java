package com.example;

import com.example.entity.Dish;
import com.example.entity.Restaurant;
import com.example.entity.User;
import com.example.entity.Vote;
import com.example.repo.DishRepository;
import com.example.repo.RestaurantRepository;
import com.example.repo.UserRepository;
import com.example.repo.VoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.entity.Role.ADMIN;
import static com.example.entity.Role.USER;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final VoteRepository voteRepository;

    public DatabaseInitializer(UserRepository userRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User(100000, "admin", "admin@yandex.ru", "{noop}admin", ADMIN));
        userRepository.save(new User(100001, "user", "user@yandex.ru", "{noop}password", USER));

        restaurantRepository.save(new Restaurant(100002, "TanGen", new ArrayList<>(), new ArrayList<>()));
        restaurantRepository.save(new Restaurant(100003, "Myata", new ArrayList<>(), new ArrayList<>()));

        dishRepository.save(new Dish(100004, "Баклажаны в соусе", 500, restaurantRepository.getReferenceById(100002)));
        dishRepository.save(new Dish(100005, "Свинина Танцуй", 1000, restaurantRepository.getReferenceById(100002)));
        dishRepository.save(new Dish(100006, "Салат Цезарь", 100, restaurantRepository.getReferenceById(100003)));
        dishRepository.save(new Dish(100007, "Пюре с котлетой", 400, restaurantRepository.getReferenceById(100003)));

        Restaurant restaurant = restaurantRepository.findById(100002).get();
        restaurant.setDishes(Arrays.asList(dishRepository.getReferenceById(100004), dishRepository.getReferenceById(100005)));
        restaurantRepository.save(restaurant);

        Restaurant restaurant2 = restaurantRepository.findById(100003).get();
        restaurant2.setDishes(Arrays.asList(dishRepository.getReferenceById(100006), dishRepository.getReferenceById(100007)));
        restaurantRepository.save(restaurant2);

        Vote vote = new Vote(100008);
        vote.setCreated(convertDate("2024-08-29 10:00:00"));
        vote.setRestaurant(restaurantRepository.getReferenceById(100002));
        vote.setUser(userRepository.getReferenceById(100000));
        voteRepository.save(vote);

        Vote vote2 = new Vote(100009);
        vote2.setCreated(convertDate("2024-08-29 10:00:00"));
        vote2.setRestaurant(restaurantRepository.getReferenceById(100003));
        vote2.setUser(userRepository.getReferenceById(100000));
        voteRepository.save(vote2);

        Vote vote3 = new Vote(100010);
        vote3.setCreated(convertDate("2024-08-29 10:00:00"));
        vote3.setRestaurant(restaurantRepository.getReferenceById(100002));
        vote3.setUser(userRepository.getReferenceById(100001));
        voteRepository.save(vote3);

        Vote vote4 = new Vote(100011);
        vote4.setCreated(convertDate("2024-08-29 10:00:00"));
        vote4.setRestaurant(restaurantRepository.getReferenceById(100003));
        vote4.setUser(userRepository.getReferenceById(100001));
        voteRepository.save(vote4);
    }

    private LocalDateTime convertDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, formatter);
    }
}
