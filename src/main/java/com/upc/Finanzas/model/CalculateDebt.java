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
@Table(name = "calculate_debts")
public class CalculateDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "coin", nullable = false, length = 7)
    private String coin; //moneda
    @Column(name = "interest_rate", nullable = false, length = 8)
    private String interest_rate; //tipo de tasa de interés
    @Column(name = "interest_rate_percentage", nullable = false, length = 10)
    private Double interest_rate_percentage; //porcentaje de tasa de interés
    @Column(name = "grace_period", nullable = false, length = 25)
    private Long grace_period; //periodo de gracia
    @Column(name = "type_grace_period", nullable = false, length = 7)
    private String type_grace_period; //tipo de periodo de gracia
    @Column(name = "cost_vehicle", nullable = false, length = 25)
    private Long cost_vehicle; //costo del vehiculo
    @Column(name = "term_of_loan", nullable = false, length = 25)
    private Long term_of_loan; //plazo del prestamo
    @Column(name = "type_of_term", nullable = false, length = 25)
    private String type_of_term; //tipo de plazo del prestamo
    //relaciones
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,
    foreignKey = @ForeignKey(name = "FK_CALCULATEDEBT_CLIENT_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_DEBTRESULT_USER_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
