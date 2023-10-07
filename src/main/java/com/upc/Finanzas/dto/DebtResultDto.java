package com.upc.Finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebtResultDto {
    private Double fee_payable; //cuota a pagar
    private Double amortization; //amortización
    private Double interests; //intereses
    private Double outstanding_debt; //deuda pendiente
    private Long clientId;
    private Long userId;
}
