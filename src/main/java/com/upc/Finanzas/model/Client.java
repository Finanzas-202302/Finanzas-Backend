package com.upc.Finanzas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstname;
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastname;
    @Column(name = "email", nullable = false, length = 35)
    private String email;
    @Column(name = "dni", nullable = false, length = 8)
    private Long dni;
    @Column(name = "vehicle", nullable = false, length = 25)
    private String vehicle;
    //relaciones
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_CLIENT_USER_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CalculateDebt> calculateDebts;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<DebtResult> debtResults;
}
