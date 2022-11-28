package com.astra.drunken.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Data
@Entity
public class Crate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z-0-9]*$", message = "Invalid Input")
    private String name;

    @URL
    private String cratePic;

    @Min(0)
    private int noOfBottles;

    @Min(0)
    private double price;

    @Min(0)
    private int crateInStock;

}
