package com.upc.Finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebtResultDto {
    private Double fee_payable;
    private Double amortization;
    private Double interests;
    private Double outstanding_debt;
    private Double van;
    private Double tir;
    private Long clientId;
}
