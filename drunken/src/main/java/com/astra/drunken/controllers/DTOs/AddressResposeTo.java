package com.astra.drunken.controllers.DTOs;

import com.astra.drunken.models.Address;
import com.astra.drunken.models.AddressType;
import com.astra.drunken.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class AddressResposeTo {

    private String street;
    private String number;
    private String postalCode;
    private Set<AddressType> addressType;


    public AddressResposeTo(User user, AddressType addressType) {
        user.getAddress().stream().filter(address -> address.getAddressType() == addressType);
        if (user.getAddress() != null) {
            user.getAddress().forEach(address -> {
                if (address.getAddressType() == addressType) {
                    this.street = address.getStreet() == null ? "XYZ Street" : address.getStreet();
                    this.number = address.getNumber() == null ? "XYZ Number" : address.getNumber();
                    this.postalCode = address.getPostalCode() == null ? "XYZ PostCode" : address.getPostalCode();
                } else {
                    this.street = "";
                    this.number = "";
                    this.postalCode = "";
                }

            });

        } else {
            this.street = "";
            this.number = "";
            this.postalCode = "";
        }
    }
}
