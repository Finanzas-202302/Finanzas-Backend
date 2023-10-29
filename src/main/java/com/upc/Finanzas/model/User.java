package com.upc.Finanzas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstname;
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastname;
    @Column(name = "email", nullable = false, length = 35)
    private String email;
    @Column(name = "password", nullable = false, length = 25)
    private String password;
    //relaciones
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Client> clients;
}
