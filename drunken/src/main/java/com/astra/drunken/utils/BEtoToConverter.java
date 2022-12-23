package com.astra.drunken.utils;

import com.astra.drunken.controllers.DTOs.ProductResponseTO;
import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.Crate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BEtoToConverter {
    public static void convertToProductResponse(List<Bottle> bottle) {
    }

    public static List<ProductResponseTO> convertBottleBEToTO(List<Bottle> bottleBEs) {
        return bottleBEs.stream().map(ProductResponseTO::new
        ).collect(Collectors.toList());
    }

    public static List<ProductResponseTO> convertCrateBEToTO(List<Crate> crateBEs) {
        return crateBEs.stream().map(ProductResponseTO::new
        ).collect(Collectors.toList());
    }
}
