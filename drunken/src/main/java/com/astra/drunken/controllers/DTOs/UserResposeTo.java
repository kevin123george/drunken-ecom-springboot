package com.astra.drunken.controllers.DTOs;

import com.astra.drunken.models.Address;
import com.astra.drunken.models.User;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserResposeTo {

    private String userName;
    private Date birthDate;
    private Set<Address> address;

    public UserResposeTo(User user) {
        this.userName = user.getUserName();
        this.birthDate = user.getBirthDate();
        if(user.getAddress().size() == 0) this.address = null;
        else this.address = user.getAddress();
    }
}
