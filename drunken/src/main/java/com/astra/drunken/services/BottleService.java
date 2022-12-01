package com.astra.drunken.services;

import com.astra.drunken.models.Bottle;
import com.astra.drunken.repositories.BottleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BottleService {

    @Autowired
    private BottleRepo bottleRepo;


    public List<Bottle> getAllBottles() {
        return bottleRepo.findAll();
    }

    public Optional<Bottle> getProductById(Long id) {
        return bottleRepo.findById(id);
    }

    public void addRandom() {
        var bottle = new Bottle();
        bottle.setName(String.valueOf(UUID.randomUUID()));
        bottle.setInStock(34);
        bottle.setIsAlcoholic(true);
        bottle.setPrice(343.0);
        bottle.setVolume(54.3);
        bottle.setSupplier("astra drug");
        bottleRepo.save(bottle);

    }

    public List<Bottle> productSearch(String q){
        return bottleRepo.findByNameContaining(q);
    }

}
