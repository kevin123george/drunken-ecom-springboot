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
    @Pattern(regexp = "^[A-Za-z-0-9]*$", message = "Invalid Input")
    private String name;


    private String bottlePic;

    @Min(0)
    private Double volume;

    private Boolean isAlcoholic = Boolean.FALSE;

    // TODO: 19.11.22  handle this in service  volumePercent: double (when value > 0.0, than isAlcoholic true)
    private Double volumePercent;

    @Min(0)
    private Double price;

    @NotEmpty
    @NotNull
    private String supplier;

    @Min(0)
    private int inStock;
}
