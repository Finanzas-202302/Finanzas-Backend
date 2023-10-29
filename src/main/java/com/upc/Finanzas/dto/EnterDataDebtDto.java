package com.upc.Finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterDataDebtDto {
    private String coin; //moneda
    private String interest_rate; //tipo de tasa de interés
    private Double interest_rate_percentage; //porcentaje de tasa de interés
    private Long grace_period; //periodo de gracia
    private String type_grace_period; //tipo de periodo de gracia
    private Double cost_vehicle; //costo del vehiculo
    private Long term_of_loan; //plazo del prestamo
    private String type_of_term; //tipo de plazo del prestamo
    private Long clientId;
}
