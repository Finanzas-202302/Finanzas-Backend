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
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "saldo_inicial_cuota_final", nullable = false, length = 100)
    private double saldoInicialCuotaFinal;
    @Column(name = "interes", nullable = false, length = 100)
    private double interes;
    @Column(name = "cuota", nullable = false, length = 100)
    private double cuota;
    @Column(name = "amortizacion", nullable = false, length = 100)
    private double amortizacion;
    @Column(name = "seguro_desg_cuota", nullable = false, length = 100)
    private double seguroDesgCuota;
    @Column(name = "seguro_riesgo_grilla", nullable = false, length = 100)
    private double seguroRiesgoGrilla;
    @Column(name = "gps", nullable = false, length = 100)
    private double GPS;
    @Column(name = "portes", nullable = false, length = 100)
    private double portes;
    @Column(name = "gastos_admin", nullable = false, length = 100)
    private double gastosAdmin;
    @Column(name = "saldo_final_para_cuota", nullable = false, length = 100)
    private double saldoFinalParaCuota;
    @Column(name = "saldo_inicial_cuota", nullable = false, length = 100)
    private double saldoInicialCuota;
    @Column(name = "periodo_gracia", nullable = false, length = 100)
    private double periodoGracia;
    @Column(name = "flujo", nullable = false, length = 100)
    private double flujo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RESULT_CREDIT_ID"))
    private Credit credit;
}
