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

@NoArgsConstructor
@Data
@Entity
public class Bottle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z-0-9]*$", message = "Only containing letters and digits")
    private String name;


    @URL
    private String bottlePic;

    @Min(value = 0, message = "volume cannot be negative ")
    private Double volume;

    private Boolean isAlcoholic = Boolean.FALSE;

    private Double volumePercent = 0.0;

    @Min(0)
    private Double price;

    @NotEmpty
    @NotNull
    private String supplier;

    @Min(0)
    private int inStock;
}
