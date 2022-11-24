package com.astra.drunken.services;

import com.astra.drunken.models.Bottle;
import com.astra.drunken.repositories.BottleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
