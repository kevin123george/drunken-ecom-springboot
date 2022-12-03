package com.astra.drunken.controllers.DTOs;

import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.Crate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class ProductResponseTO {

    private Long id;

    private String name;

    private String bottlePic;

    private Double volume;

    private Boolean isAlcoholic;

    private Double volumePercent;

    private Double price;

    private String supplier;

    private int inStock;

    private String cratePic;

    private int noOfBottles;

    private int crateInStock;

    private String productType;

    public ProductResponseTO(Bottle bottle) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.bottlePic = bottle.getBottlePic();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.getIsAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
        this.productType = "bottle";
    }

    public ProductResponseTO(Crate crate) {
        this.id = crate.getId();
        this.name = crate.getName();
        this.bottlePic = crate.getCratePic();
        this.price = crate.getPrice();
        this.noOfBottles = crate.getNoOfBottles();
        this.crateInStock = crate.getCrateInStock();
        this.productType = "crate";
    }
}
