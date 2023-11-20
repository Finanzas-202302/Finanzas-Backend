package com.upc.Finanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(name = "periodo_de_capitalizacion", nullable = true, length = 25)
    private String periodo_de_capitalizacion;
    //CAPITALIZACION ESPECIAL
    @Column(name = "capitalizacion_especial", nullable = true, length = 10)
    private Integer capitalizacion_especial;
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fecha_prestamo;
    // PLAZO DE TASA: MENSUAL, BIMESTRAL, ETC
    @Column(name = "plazo_tasa_interes", nullable = false, length = 25)
    private String plazo_tasa_interes;
    //PLAZO DE TASA ESPECIAL
    @Column(name = "plazo_interes_especial", nullable = true, length = 10)
    private Integer plazo_interes_especial;
    // TASA DE INTERÉS EN PORCENTAJE (SE AGREGA EL VALOR SIN %: SI ES 10% SE INGRESA 10)
    @Column(name = "interest_rate_percentage", nullable = false, length = 10)
    private Double interest_rate_percentage;
    ////////////////////////////
    // PERIODO DE GRACIA EN MESES
    @Column(name = "grace_period", nullable = true, length = 25)
    private Long grace_period;
    // TIPO DE PERIODO DE GRACIA: PARCIAL, TOTAL O SIN PERIODO DE GRACIA
    @Column(name = "type_grace_period", nullable = false, length = 25)
    private String type_grace_period;
    /////////////////////////
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
    // VFMG: 40% O 50%
    @Column(name = "vfmg_percentage", nullable = false, length = 10)
    private Long vfmg_percentage;
    // PORCENTAJE DE CREDITO A PAGAR EN EL PLAZO: 30% O 40%
    @Column(name = "credit_percentage", nullable = false, length = 10)
    private Long credit_percentage;
    // COK
    @Column(name = "cok", nullable = true, length = 50)
    private Double COK;
    @Column(name = "van", nullable = true, length = 50)
    private Double VAN;
    // COSTOS INICIALES
    @Column(name = "costos_notariales", nullable = true, length = 25)
    private Double costos_notariales;
    @Column(name = "costos_notariales_bool", nullable = true, length = 25)
    private Boolean costos_notariales_bool;
    @Column(name = "costos_registrales", nullable = true, length = 25)
    private Double costos_registrales;
    @Column(name = "costos_registrales_bool", nullable = true, length = 25)
    private Boolean costos_registrales_bool;
    @Column(name = "tasacion", nullable = true, length = 25)
    private Double tasacion;
    @Column(name = "tasacion_bool", nullable = true, length = 25)
    private Boolean tasacion_bool;
    @Column(name = "estudio_de_titulos", nullable = true, length = 25)
    private Double estudio_de_titulos;
    @Column(name = "estudio_de_titulos_bool", nullable = true, length = 25)
    private Boolean estudio_de_titulos_bool;
    @Column(name = "otros_costes", nullable = true, length = 25)
    private Double otros_costes;
    @Column(name = "otros_costes_bool", nullable = true, length = 25)
    private Boolean otros_costes_bool;
    //COSTOS PERIODICOS
    @Column(name = "portes", nullable = true, length = 25)
    private Double portes;
    @Column(name = "gastos_administrativos", nullable = true, length = 25)
    private Double gastos_administrativos;
    @Column(name = "comision", nullable = true, length = 25)
    private Double comision;
    @Column(name = "penalidad", nullable = true, length = 25)
    private Double penalidad;
    @Column(name = "comunicacion", nullable = true, length = 25)
    private Double comunicacion;
    @Column(name = "seguridad", nullable = true, length = 25)
    private Double seguridad;
    @Column(name = "otros", nullable = true, length = 25)
    private Double otros;
    //relaciones
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_CALCULATEDEBT_CLIENT_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Client client;

    @OneToMany(mappedBy = "calculateDebt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentPlan> paymentPlans = new ArrayList<>();
}

