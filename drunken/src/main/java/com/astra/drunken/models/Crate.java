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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Data
@Entity
public class Crate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "name cannot be empty")
    @Pattern(regexp = "^[A-Za-z-0-9]*$", message = "Only containing letters and digits")
    private String name;

//    @URL
    @NotNull
    @Pattern(regexp = "(https:\\/\\/).*\\.(?:jpg|gif|png)", message = "must be a valid URL to picture")
    private String cratePic;

    @Min(1)
    private int noOfBottles;

    @Positive(message = "price must be positive , greater than 0")
    private double price;

    @Min(0)
    private int crateInStock;

}
