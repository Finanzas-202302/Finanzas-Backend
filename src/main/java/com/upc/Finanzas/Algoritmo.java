package com.upc.Finanzas;

import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.PaymentPlan;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Algoritmo {
    private static CalculateDebt info;
    private static Double tasa_de_interes;
    private static Double cuota_inicial;
    private static Double financiar;
    private static Double interes;
    private static Double COK;
    private static Double seguro_desgravamen;
    private static Double financiamiento;
    private static Double amortizacion;
    private static Double cuota_final;
    private static Double costo_vehiculo;
    private static Double costo_prestamo;
    private static Double seguro_desgravamen_tasa;
    private static Double plazo;
    public Algoritmo(CalculateDebt calculateDebt){
        this.info = calculateDebt;
        costo_vehiculo = 0.0;
        cuota_inicial = 0.0;
        financiar = 0.0;
        financiamiento = 0.0;
        cuota_final = 0.0;
        plazo = 0.0;
        COK = 0.0;
        costo_prestamo = 0.0;
        seguro_desgravamen = 0.0;
        amortizacion = 0.0;
    }
    public static void convertData(){

        //CONVERTIR PLAZO DE TASA
        if(info.getPlazo_tasa_interes().equals("anual")) plazo = 360.0;
        if(info.getPlazo_tasa_interes().equals("semestral")) plazo = 180.0;
        if(info.getPlazo_tasa_interes().equals("cuatrimestral")) plazo = 120.0;
        if(info.getPlazo_tasa_interes().equals("trimestral")) plazo = 90.0;
        if(info.getPlazo_tasa_interes().equals("bimestral")) plazo = 60.0;
        if(info.getPlazo_tasa_interes().equals("mensual")) plazo = 30.0;
        if(info.getPlazo_tasa_interes().equals("quincenal")) plazo = 15.0;
        if(info.getPlazo_tasa_interes().equals("diario")) plazo = 1.0;
        if(info.getPlazo_tasa_interes().equals("especial")) plazo = Double.valueOf(info.getPlazo_interes_especial());

        //CONVERTIR TASA DE INTERÉS
        if(info.getInterest_rate().toLowerCase().equals("nominal")){
            if(info.getPeriodo_de_capitalizacion().equals("anual")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/360.0))),(30/360.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("semestral")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/180.0))),(30/180.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("cuatrimestral")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/120.0))),(30/120.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("trimestral")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/90.0))),(30/90.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("bimestral")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/60.0))),(30/60.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("mensual")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/30.0))),(30/30.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("quincenal")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/15.0))),(30/150.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("diario")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/1.0))),(30/1.0)) - 1;
            if(info.getPeriodo_de_capitalizacion().equals("especial")) tasa_de_interes = Math.pow((1 + ((info.getInterest_rate_percentage()/100.0)/(plazo/Double.valueOf(info.getCapitalizacion_especial())))),(30/Double.valueOf(info.getCapitalizacion_especial()))) - 1;
        } else if (info.getInterest_rate().toLowerCase().equals("efectiva")) tasa_de_interes = Math.pow(1 + (info.getInterest_rate_percentage()/100.0), (30/plazo)) - 1;

        //COSTOS INICIALES DE LA COMPRA INTELIGENTE
        if(info.getCostos_notariales() != null){
            if(info.getCostos_notariales_bool()) financiar += info.getCostos_notariales();
            else financiar += 0.0;
        }
        if(info.getCostos_registrales() != null){
            if(info.getCostos_registrales_bool()) financiar += info.getCostos_registrales();
            else financiar += 0.0;
        }
        if(info.getTasacion() != null){
            if(info.getTasacion_bool()) financiar += info.getTasacion();
            else financiar += 0.0;
        }
        if(info.getEstudio_de_titulos() != null){
            if(info.getEstudio_de_titulos_bool()) financiar += info.getEstudio_de_titulos();
            else financiar += 0.0;
        }
        if(info.getOtros_costes() != null){
            if(info.getOtros_costes_bool()) financiar += info.getOtros_costes();
            else financiar += 0.0;
        }
        costo_vehiculo = Double.valueOf(info.getCost_vehicle());
        costo_prestamo += costo_vehiculo;
        cuota_inicial = costo_prestamo * info.getCuota_inicial_percentage()/100.0;
        financiar = costo_prestamo * (1.0 - info.getCuota_inicial_percentage()/100.0);
        financiamiento = costo_prestamo * info.getCredit_percentage()/100.0;
        seguro_desgravamen_tasa = info.getSeguro_desgravamen()/100.0;
        COK = info.getCOK()/100.0;
    }
    public static List<PaymentPlan> calculatePaymentPlan(CalculateDebt calculateDebt){
        convertData();
        Double interes_sobre_financiamiento;
        Double desgravamen_sobre_financiamiento;
        Double cuota_financiamiento;
        Double financiamiento_aux = financiamiento;
        Double prestamo_aux = financiar;
        Double flujo_aux = 0.0;
        Double saldo_final = 0.0;
        Double portes = calculateDebt.getPortes();
        Double gastos_administrativos = calculateDebt.getGastos_administrativos();
        Double comision = calculateDebt.getComision();
        Double penalidad = calculateDebt.getPenalidad();
        Double comunicacion = calculateDebt.getComunicacion();
        Double seguridad = calculateDebt.getSeguridad();
        Double otros = calculateDebt.getOtros();
        Double VAN = 0.0;
        LocalDate fecha_inicial = calculateDebt.getFecha_prestamo();
        //CREAMOS LA LISTA
        List<PaymentPlan> paymentPlans = new ArrayList<>();
        interes_sobre_financiamiento = tasa_de_interes * financiamiento;
        desgravamen_sobre_financiamiento = seguro_desgravamen_tasa * financiamiento;

        for(int period_number = 0; period_number <= info.getTerm_of_loan() + 1; period_number++){
            PaymentPlan paymentPlan = new PaymentPlan();
            paymentPlan.setCalculateDebt(info);
            paymentPlan.setPeriodNumber(period_number);
            LocalDate dueDate = fecha_inicial.plusMonths(period_number);
            paymentPlan.setDueDate(dueDate);
            // Calcular los componentes del pago mensual
            if(period_number == 0){
                prestamo_aux = financiar;
                financiamiento_aux = financiamiento;
                amortizacion = 0.0;
                interes = 0.0;
                seguro_desgravamen = 0.0;
                cuota_financiamiento = 0.0;
                flujo_aux = prestamo_aux;
                VAN += flujo_aux;
            }
            else {
                if(period_number == (info.getTerm_of_loan() + 1)){
                    amortizacion = prestamo_aux;
                    interes = prestamo_aux * tasa_de_interes;
                    seguro_desgravamen = prestamo_aux * seguro_desgravamen_tasa;
                    cuota_financiamiento = amortizacion + interes + seguro_desgravamen;
                    financiamiento_aux = 0.0;
                    prestamo_aux = 0.0;
                    flujo_aux = (cuota_financiamiento + portes + gastos_administrativos + comision + penalidad + comunicacion + seguridad + otros) * -1;
                    VAN += (flujo_aux / Math.pow((1 + COK),period_number));
                } else {
                    interes = tasa_de_interes * prestamo_aux;
                    seguro_desgravamen = seguro_desgravamen_tasa * prestamo_aux;
                    cuota_financiamiento = (financiamiento * (tasa_de_interes + seguro_desgravamen_tasa)) / (1 - Math.pow((1 + (tasa_de_interes + seguro_desgravamen_tasa)), (info.getTerm_of_loan() * -1)));
                    amortizacion = cuota_financiamiento - financiamiento_aux * tasa_de_interes - financiamiento_aux * seguro_desgravamen_tasa;
                    prestamo_aux = prestamo_aux - amortizacion;
                    financiamiento_aux = financiamiento_aux - amortizacion;
                    flujo_aux = (cuota_financiamiento + portes + gastos_administrativos + comision + penalidad + comunicacion + seguridad + otros) * -1;
                    VAN += (flujo_aux / Math.pow((1 + COK),period_number));
                }
            }

            if(period_number == info.getTerm_of_loan()) {
                financiamiento_aux = Math.floor(financiamiento_aux);
            }

            // Configurar los valores en el objeto PaymentPlan
            paymentPlan.setFinanciamiento(financiamiento_aux);
            paymentPlan.setInteres(interes);
            paymentPlan.setAmortizacion(amortizacion);
            paymentPlan.setSeguro_desgravamen(seguro_desgravamen);
            paymentPlan.setCuota_financiamiento(cuota_financiamiento);
            paymentPlan.setPrestamo(prestamo_aux);
            paymentPlan.setFlujo_total(flujo_aux);
            paymentPlan.setSeguridad(seguridad);
            paymentPlan.setPortes(portes);
            paymentPlan.setPenalidad(penalidad);
            paymentPlan.setOtros(otros);
            paymentPlan.setGastos_administrativos(gastos_administrativos);
            paymentPlan.setComunicacion(comunicacion);
            paymentPlan.setComision(comision);
            paymentPlans.add(paymentPlan);
        }
        calculateDebt.setVAN(VAN);
        //calculateDebt.setTIR(convertTIR(calculateDebt, paymentPlans));
        return paymentPlans;
    }
}
