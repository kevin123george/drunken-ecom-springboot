package com.astra.drunken.controllers.DTOs;

import com.astra.drunken.models.Address;
import com.astra.drunken.models.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
