package com.upc.Finanzas;

import com.upc.Finanzas.model.CalculateDebt;

import java.util.List;

public class Algoritmo {
    public static void calcularCuota(CalculateDebt debt_info){
        //DEFINIR VARIABLES QUE SE INGRESAN
        Double precio_vehiculo = debt_info.getCost_vehicle().doubleValue();
        Double cuota_inicial = (debt_info.getCuota_inicial_percentage().doubleValue() / 100.0) * precio_vehiculo; // 20% o 30%
        Double prestamo = precio_vehiculo - cuota_inicial;
        Double financiamiento = precio_vehiculo * (debt_info.getCredit_percentage().doubleValue() / 100.0); //30% o 40%
        Double cuoton = precio_vehiculo * (debt_info.getVfmg_percentage().doubleValue() / 100.0); //50% o 40%, cuota final
        Double cok = debt_info.getCOK() / 100.0;
        Double plazo = debt_info.getTerm_of_loan().doubleValue(); //24 o 36
        Double costos_periodicos = debt_info.getCostos_periodicos();
        Double costos_iniciales = debt_info.getCostos_iniciales();
        String tipo_tasa_interes = debt_info.getInterest_rate().toLowerCase();
        Double tasa_interes_porcentaje = debt_info.getInterest_rate_percentage();
        Integer plazo_tasa_interes = 0;
        Integer periodo_capitalizacion = 0;
        //CAMBIAR TASA EFECTIVA A MENSUAL (NOMINAL A EFECTIVA)
        switch (debt_info.getPlazo_tasa_interes().toLowerCase()) {
            case "anual" -> plazo_tasa_interes = 360;
            case "semestral" -> plazo_tasa_interes = 180;
            case "cuatrimestral" -> plazo_tasa_interes = 120;
            case "trimestral" -> plazo_tasa_interes = 90;
            case "bimestral" -> plazo_tasa_interes = 60;
            case "mensual" -> plazo_tasa_interes = 30;
            case "quincenal" -> plazo_tasa_interes = 15;
            case "diario" -> plazo_tasa_interes = 1;
            //agregar especial
        }
        switch (debt_info.getPeriodo_de_capitalizacion().toLowerCase()) {
            case "anual" -> periodo_capitalizacion = 360;
            case "semestral" -> periodo_capitalizacion = 180;
            case "cuatrimestral" -> periodo_capitalizacion = 120;
            case "trimestral" -> periodo_capitalizacion = 90;
            case "bimestral" -> periodo_capitalizacion = 60;
            case "mensual" -> periodo_capitalizacion = 30;
            case "quincenal" -> periodo_capitalizacion = 15;
            case "diario" -> periodo_capitalizacion = 1;
            //agregar especial
        }
        if(tipo_tasa_interes == "nominal"){
            tasa_interes_porcentaje = Math.pow(1 + (debt_info.getInterest_rate_percentage() / 100) / (periodo_capitalizacion / plazo_tasa_interes), 30 / periodo_capitalizacion) - 1;
        }
        else-if(tipo_tasa_interes == "efectiva"){
            tasa_interes_porcentaje = Math.pow(1 + (debt_info.getInterest_rate_percentage() / 100), (30/plazo_tasa_interes));
        }
        /*
        porcentaje de seguro desgravamen
        if(tipo de interes == nominal) formula
        else == debt_info.getPorcentaje de desgravamen
        */
        /*
        logica para el periodo de gracia
        */

        //DEFINIR VARIABLES QUE SE CALCULAN
        double intereses;
        double amortizacion;
        double seguro_desgravamen;
    }
}
