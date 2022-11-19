package com.astra.drunken.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: 19.11.22 add not empty 
    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String number;

    @NotNull
    @Size(min = 5, max = 5)
    private String postalCode;

    @OneToOne(mappedBy = "address")
    private User user;
}
