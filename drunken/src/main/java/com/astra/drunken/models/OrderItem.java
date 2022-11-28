package com.astra.drunken.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@Data
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

}
