package com.upc.Finanzas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "algoritmo_result")
public class Result {
    public Double financiamiento;
    public Double prestamo;
    public Double amortizacion;
    public Double intereses;
    public Double seguro_desgravamen;
    public Double cuota_financiamiento;
    public Double flujo_caja;
}

/*
       Format f = new SimpleDateFormat("MM/dd/yy");
      String strDate = f.format(new Date());
      System.out.println("Current Date = "+strDate);
*/