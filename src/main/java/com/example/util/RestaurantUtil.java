package com.example.util;

import com.example.entity.Restaurant;
import com.example.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), new ArrayList<>(), new ArrayList<>());
    }
}
