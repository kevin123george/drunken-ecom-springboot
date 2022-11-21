package com.astra.drunken.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name =  "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String username;

    private String password;

    // TODO: 19.11.22 add validation birthday: LocalDate(>1.1.1900, <today)
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private Set<Order> orders;

}
