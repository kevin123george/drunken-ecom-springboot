package com.astra.drunken.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "street cannot be empty")
    private String street;

    @NotNull
    @NotEmpty(message = "number cannot be empty")
    private String number;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 5, message = "should only have 5 digits")
    @Pattern(regexp = "^[0-9]*$", message = "should only have digits")
    private String postalCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private AddressType addressType = AddressType.delivery;
}
