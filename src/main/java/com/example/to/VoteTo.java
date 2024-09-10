package com.example.to;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class VoteTo extends BaseTo {

    LocalDateTime created;

    int restaurantId;

    public VoteTo(Integer id, LocalDateTime created, int restaurantId) {
        super(id);
        this.created = created;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                ", restaurantId=" + restaurantId +
                ", id=" + id +
                '}';
    }
}