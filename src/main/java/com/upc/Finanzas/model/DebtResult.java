package com.upc.Finanzas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "debt_results")
public class DebtResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fee_payable", nullable = false, length = 25)
    private Double fee_payable; //cuota a pagar
    @Column(name = "amortization", nullable = false, length = 25)
    private Double amortization; //amortización
    @Column(name = "interests", nullable = false, length = 25)
    private Double interests; //intereses
    @Column(name = "outstanding_debt", nullable = false, length = 25)
    private Double outstanding_debt; //deuda pendiente
    //relaciones
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_DEBTRESULT_CLIENT_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_DEBTRESULT_USER_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
