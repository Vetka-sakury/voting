package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
//todo index
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends AbstractNamedEntity {

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime created;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @NotNull
    private Restaurant restaurant;

    public Dish(Integer id, String name, double price) {
        this(id, name, LocalDateTime.now(), price, null);
    }

    public Dish(Integer id, String name, double price, Restaurant restaurant) {
        this(id, name, LocalDateTime.now(), price, restaurant);
    }

    public Dish(Integer id, String name, LocalDateTime created, double price, Restaurant restaurant) {
        super(id, name);
        this.created = created;
        this.price = price;
        this.restaurant = restaurant;
    }
}
