package com.example.util;

import com.example.entity.Dish;
import com.example.to.DishTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice());
    }
}
