package com.upc.Finanzas.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDto {
    private double saldoInicialCuotaFinal;
    private double interes;
    private double cuota;
    private double amortizacion;
    private double seguroDesgCuota;
    private double seguroRiesgoGrilla;
    private double GPS;
    private double portes;
    private double gastosAdmin;
    private double saldoFinalParaCuota;
    private double saldoInicialCuota;
    private double periodoGracia;
    private double flujo;
    private Long creditId;
}
