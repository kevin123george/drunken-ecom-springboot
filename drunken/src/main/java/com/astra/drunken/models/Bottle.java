package com.astra.drunken.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.Nullable;

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
public class Bottle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "name cannot be empty")
    @Pattern(regexp = "^[A-Za-z-0-9]*$", message = "Only containing letters and digits")
    private String name;


    @Pattern(regexp = "(https:\\/\\/).*\\.(?:jpg|gif|png)", message = "must be a valid URL to picture")
    private String bottlePic;

    @Min(value = 0, message = "volume cannot be negative ")
    private Double volume;

    private Boolean isAlcoholic = Boolean.FALSE;

    private Double volumePercent = 0.0;

    @Positive(message = "price must be positive , greater than 0")
    private Double price;

    @NotEmpty(message = "should have a supplier")
    @NotNull
    private String supplier;

    @Min(0)
    private int inStock;
}
