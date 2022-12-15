package com.astra.drunken.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "crateId", referencedColumnName = "id")
    private Crate crate;

    @OneToOne
    @JoinColumn(name = "bottleId", referencedColumnName = "id")
    private Bottle bottle;

    @OneToOne
    @JoinColumn(name = "orderItem", referencedColumnName = "id")
    private OrderItem orderItem;

}
