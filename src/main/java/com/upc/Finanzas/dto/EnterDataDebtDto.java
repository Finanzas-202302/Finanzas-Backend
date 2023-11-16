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
    private String plazo_tasa_interes;
    private Double interest_rate_percentage;
    private Long cuota_inicial_percentage;
    private Long cost_vehicle;
    private Long term_of_loan;
    private Double seguro_desgravamen;
    private Long vfmg_percentage;
    private Long credit_percentage;
    private Long cliendId;
}
