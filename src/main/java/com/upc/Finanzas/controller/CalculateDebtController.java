package com.upc.Finanzas.controller;

import com.upc.Finanzas.exception.ValidationException;
import com.upc.Finanzas.model.CalculateDebt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank/v1/calculate-debt")
public class CalculateDebtController {
    private void validateInfo(CalculateDebt calculateDebt){
        String coin = calculateDebt.getCoin().toLowerCase();
        String interesRate = calculateDebt.getInterest_rate().toLowerCase();
        if(calculateDebt.getCoin() == null || calculateDebt.getCoin().isEmpty()) throw new ValidationException("La moneda es obligatoria");
        if(!(coin.equals("soles") || coin.equals("dolares"))) throw new ValidationException("Ingrese una moneda correcta (Soles o Dolares)");
        if(calculateDebt.getInterest_rate() == null || calculateDebt.getInterest_rate().isEmpty()) throw new ValidationException("El tipo de tasa de interes es obligatorio");
        if(!(interesRate.equals("nominal") || interesRate.equals("efectiva"))) throw new ValidationException("Ingrese un tipo de tasa de interes correcto (Nominal o Efectiva");
        if(calculateDebt.getInterest_rate_percentage() == null) throw new ValidationException("El porcentaje de tasa de interes es obligatorio");
    }
}
