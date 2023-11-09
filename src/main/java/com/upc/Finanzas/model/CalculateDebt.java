package com.upc.Finanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    //MONEDA
    @Column(name = "coin", nullable = false, length = 7)
    private String coin;
    // TIPO DE TASA DE INTERÉS: NOMINAL O EFECTIVA
    @Column(name = "interest_rate", nullable = false, length = 8)
    private String interest_rate;
    //PERIODO DE CAPITALIZACIÓN: MENSUAL, BIMESTRAL, ETC
    @Column(name = "periodo_de_capitalizacion", nullable = false, length = 25)
    private String periodo_de_capitalizacion;
    // PLAZO DE TASA: MENSUAL, BIMESTRAL, ETC
    @Column(name = "plazo_tasa_interes", nullable = false, length = 25)
    private String plazo_tasa_interes;
    //PLAZO DE TASA ESPECIAL
    @Column(name = "plazo_interes_especial", nullable = false, length = 10)
    private Integer plazo_interes_especial;
    // TASA DE INTERÉS EN PORCENTAJE (SE AGREGA EL VALOR SIN %: SI ES 10% SE INGRESA 10)
    @Column(name = "interest_rate_percentage", nullable = false, length = 10)
    private Double interest_rate_percentage;
    // PERIODO DE GRACIA EN MESES
    @Column(name = "grace_period", nullable = false, length = 25)
    private Long grace_period;
    // TIPO DE PERIODO DE GRACIA: PARCIAL, TOTAL O SIN PERIODO DE GRACIA
    @Column(name = "type_grace_period", nullable = false, length = 25)
    private String type_grace_period;
    // PORCENTAJE DE CUOTA INICIAL: 20% O 30%
    @Column(name = "initial_fee_percentage", nullable = false, length = 25)
    private Long cuota_inicial_percentage;
    // COSTO DEL VEHÍCULO
    @Column(name = "cost_vehicle", nullable = false, length = 25)
    private Long cost_vehicle;
    // PLAZO A PAGAR EL PRÉSTAMO: 24 O 36 CUOTAS MENSUALES
    @Column(name = "term_of_loan", nullable = false, length = 25)
    private Long term_of_loan;
    // PORCENTAJE DE SEGURO DESGRAVAMEN
    @Column(name = "degravamen", nullable = false, length = 25)
    private Double seguro_desgravamen;
    // TIPO DE TASA DE SEGURO DESGRAVAMEN: EFECTIVA O NOMINAL
    @Column(name = "desgravamen_tipo", nullable = false, length = 25)
    private String desgravamen_tipo;
    // VFMG: 40% O 50%
    @Column(name = "vfmg_percentage", nullable = false, length = 10)
    private Long vfmg_percentage;
    // PORCENTAJE DE CREDITO A PAGAR EN EL PLAZO: 30% O 40%
    @Column(name = "credit_percentage", nullable = false, length = 10)
    private Long credit_percentage;
    // COK
    @Column(name = "cok", nullable = false, length = 10)
    private Double COK;
    // COSTOS PERIODICOS
    @Column(name = "costos_periodicos", nullable = false, length = 25)
    private Double costos_periodicos;
    // COSTOS INICIALES
    @Column(name = "costos_iniciales", nullable = false, length = 25)
    private Double costos_iniciales;
    // CUOTA INICIAL VALOR: 20% o 30%
    @Column(name = "initial_fee", nullable = true, length = 25)
    private Double cuota_inicial;
    //Lista de proceso
    //private List<Result> lista_resultado;
    // VFMG VALOR: 40% o 50%
    @Column(name = "vfmg", nullable = true, length = 25)
    private Double vfmg;
    // CREDITO A PAGAR VALOR: 30% o 40%
    @Column(name = "credit", nullable = true, length = 25)
    private Double credit;
    // VAN
    @Column(name = "van", nullable = true, length = 25)
    private Double van;
    // TIR
    @Column(name = "tir", nullable = true, length = 25)
    private Double tir;
    //relaciones
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_CALCULATEDEBT_CLIENT_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Client client;
}

