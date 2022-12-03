package com.astra.drunken.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private Double price;

    @Pattern(regexp = "^[0-9]*$", message = "Invalid Input")
    private String position;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Order order) {
        this.order = order;
    }


}
