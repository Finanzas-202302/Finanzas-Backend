package com.upc.Finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPlanDto {
    private Long CalculateDebtId;
    private Integer periodNumber;
    private LocalDate dueDate;
    private Double prestamo;
    private Double financiamiento;
    private Double flujo_total;
    private Double interes;
    private Double cuota_financiamiento;
    private Double amortizacion;
    private Double seguro_desgravamen;
    private Double portes;
    private Double gastos_administrativos;
    private Double comision;
    private Double penalidad;
    private Double comunicacion;
    private Double seguridad;
    private Double otros;
}
