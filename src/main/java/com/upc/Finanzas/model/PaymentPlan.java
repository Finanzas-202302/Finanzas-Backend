package com.upc.Finanzas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_plan")
public class PaymentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "calculate_debt_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PAYMENT_CALCULATE_DEBT_ID"))
    private CalculateDebt calculateDebt;

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "prestamo", nullable = false)
    private Double prestamo;

    @Column(name = "financiamiento", nullable = false)
    private Double financiamiento;
    @Column(name = "flujo_total", nullable = false)
    private Double flujo_total;

    @Column(name = "interes", nullable = false)
    private Double interes;

    @Column(name = "cuota_financiamiento", nullable = false)
    private Double cuota_financiamiento;

    @Column(name = "amortizacion", nullable = false)
    private Double amortizacion;

    @Column(name = "seguro_desgravamen", nullable = false)
    private Double seguro_desgravamen;

    @Column(name = "portes", nullable = false, length = 25)
    private Double portes;

    @Column(name = "gastos_administrativos", nullable = false, length = 25)
    private Double gastos_administrativos;

    @Column(name = "comision", nullable = false, length = 25)
    private Double comision;

    @Column(name = "penalidad", nullable = false, length = 25)
    private Double penalidad;

    @Column(name = "comunicacion", nullable = false, length = 25)
    private Double comunicacion;

    @Column(name = "seguridad", nullable = false, length = 25)
    private Double seguridad;

    @Column(name = "otros", nullable = false, length = 25)
    private Double otros;

}
