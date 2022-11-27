package com.astra.drunken.services;

import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.Crate;
import com.astra.drunken.repositories.CrateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CrateService {
    private final CrateRepo crateRepo;

    @Autowired
    public CrateService(CrateRepo crateRepo) {
        this.crateRepo = crateRepo;
    }

    public List<Crate> getAllBottles() {
        return crateRepo.findAll();
    }

    public Optional<Crate> getProductById(Long id) {
        return crateRepo.findById(id);
    }

    public void addRandom() {
        var bottle = new Crate();
        bottle.setName(String.valueOf(UUID.randomUUID()));
        crateRepo.save(bottle);

    }
}
