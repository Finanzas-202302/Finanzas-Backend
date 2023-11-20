package com.upc.Finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDto {
    private String moneda;
    private double precioVentaActivo;
    private double tipoPlan;
    private double cuotaInicialPorcentaje;
    private double cuotaFinalPorcentaje;
    private double capitalizacion;
    private double portes;
    private double tasaDescuentoPorcentaje;
    private double gastosAdmin;
    private double seguroDesgravamenPorcentaje;
    private double seguroRiesgoPorcentaje;
    private String tipoPeriodoGracia;
    private int periodoGraciaMeses;
    private double cuotasPeriodoGracia;
    private double tasa;
    private String tipoTasa;
    private double numeroAnios;
    private double tiempoDias;
    private double costesNotariales;
    private double costesRegistrales;
    private double GPS;
    private double freqPago;
    private Long clientId;
}
