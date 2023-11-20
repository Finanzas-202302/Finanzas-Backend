package com.upc.Finanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "moneda", nullable = false, length = 100)
    private String moneda;
    @Column(name = "precio_venta_activo", nullable = false, length = 100)
    private double precioVentaActivo;
    @Column(name = "tipo_plan", nullable = false, length = 100)
    private double tipoPlan;
    @Column(name = "cuota_inicial_porcentaje", nullable = false, length = 100)
    private double cuotaInicialPorcentaje;
    @Column(name = "cuota_final_porcentaje", nullable = false, length = 100)
    private double cuotaFinalPorcentaje;
    @Column(name = "capitalizacion", nullable = false, length = 100)
    private double capitalizacion;
    @Column(name = "portes", nullable = false, length = 100)
    private double portes;
    @Column(name = "tasa_descuento_porcentaje", nullable = false, length = 100)
    private double tasaDescuentoPorcentaje;
    @Column(name = "gastos_admin", nullable = false, length = 100)
    private double gastosAdmin;
    @Column(name = "seguro_desgravamen_porcentaje", nullable = false, length = 100)
    private double seguroDesgravamenPorcentaje;
    @Column(name = "seguro_riesgo_porcentaje", nullable = false, length = 100)
    private double seguroRiesgoPorcentaje;
    @Column(name = "tipo_periodo_gracia", nullable = false, length = 100)
    private String tipoPeriodoGracia;
    @Column(name = "periodo_gracia_meses", nullable = false, length = 100)
    private int periodoGraciaMeses;
    @Column(name = "cuotas_periodo_gracia", nullable = false, length = 100)
    private double cuotasPeriodoGracia;
    @Column(name = "tasa", nullable = false, length = 100)
    private double tasa;
    @Column(name = "tipo_tasa", nullable = false, length = 100)
    private String tipoTasa;
    @Column(name = "numero_anios", nullable = false, length = 100)
    private double numeroAnios;
    @Column(name = "tiempo_dias", nullable = false, length = 100)
    private double tiempoDias;
    @Column(name = "costes_notariales", nullable = false, length = 100)
    private double costesNotariales;
    @Column(name = "costes_registrales", nullable = false, length = 100)
    private double costesRegistrales;
    @Column(name = "gps", nullable = false, length = 100)
    private double GPS;
    @Column(name = "freq_pago", nullable = false, length = 100)
    private double freqPago;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_CREDIT_CLIENT_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results = new ArrayList<>();
}
