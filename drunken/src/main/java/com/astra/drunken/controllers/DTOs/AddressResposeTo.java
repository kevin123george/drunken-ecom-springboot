package com.astra.drunken.controllers.DTOs;

import com.astra.drunken.models.Address;
import com.astra.drunken.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AddressResposeTo {

    private String street;
    private String number;
    private String postalCode;


    public AddressResposeTo(User user) {

//        Optional.ofNullable(System.getProperty("property")).orElse(defaultValue);
        if (user.getAddress() != null) {

            this.street = user.getAddress().getStreet() == null ? "XYZ Street" : user.getAddress().getStreet();
            this.number = user.getAddress().getNumber() == null ? "XYZ Number" : user.getAddress().getNumber();
            this.postalCode = user.getAddress().getPostalCode() == null ? "XYZ PostCode" : user.getAddress().getPostalCode();

        } else {
            this.street = "";
            this.number = "";
            this.postalCode = "";
        }
    }
}
