package com.example.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {

    double price;

    int restaurantId;

    public DishTo(Integer id, String name, double price, int restaurantId) {
        super(id, name);
        this.price = price;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "price=" + price +
                ", restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
