package com.upc.Finanzas.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterDataDebtDto {
    private String coin;
    private String interest_rate;
    private String periodo_de_capitalizacion;
    private Integer capitalizacion_especial;
    private String plazo_tasa_interes;
    private Integer plazo_interes_especial;
    private Double interest_rate_percentage;
    private Long cuota_inicial_percentage;
    private Long cost_vehicle;
    private LocalDate fecha_prestamo;
    private Long term_of_loan;
    private Double seguro_desgravamen;
    private Long vfmg_percentage;
    private Long credit_percentage;
    //periodo de gracia
    private String type_grace_period;
    private Long grace_period;
    //
    private Double TIR;
    private Double VAN;
    private Double cok;
    //COSTOS INICIALES
    private Double costos_notariales;
    private Boolean costos_notariales_bool;
    private Double costos_registrales;
    private Boolean costos_registrales_bool;
    private Double tasacion;
    private Boolean tasacion_bool;
    private Double estudio_de_titulos;
    private Boolean estudio_de_titulos_bool;
    private Double otros_costes;
    private Boolean otros_costes_bool;
    //COSTOS PERIODICOS
    private Double portes;
    private Double gastos_administrativos;
    private Double comision;
    private Double penalidad;
    private Double comunicacion;
    private Double seguridad;
    private Double otros;
    //
    private Long cliendId;
}
