package com.astra.drunken.controllers.DTOs;

import com.astra.drunken.models.Address;
import com.astra.drunken.models.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserResposeTo {

    private String userName;
    private Date birthDate;
    private Address address;

    public UserResposeTo(User user) {
        this.userName = user.getUserName();
        this.birthDate = user.getBirthDate();
        this.address = user.getAddress();
    }
}
