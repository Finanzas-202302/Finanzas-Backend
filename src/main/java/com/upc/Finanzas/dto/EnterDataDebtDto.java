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
    private Long cost_vehicle; //costo del vehiculo
    private Long term_of_loan; //plazo del prestamo
    private Long clientId;
    private Double seguro_desgravamen;
    private Long vfmg_percentage;
    private Long cuota_inicial_percentage;
    private String desgravamen_tipo;
    private Long credit_percentage;
    private Double costos_iniciales;
    private Double costos_periodicos;
    private Double COK;
}
