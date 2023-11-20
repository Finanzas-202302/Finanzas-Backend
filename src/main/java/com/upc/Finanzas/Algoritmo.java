package com.upc.Finanzas;

import com.upc.Finanzas.model.Credit;
import com.upc.Finanzas.model.Result;

import java.util.ArrayList;
import java.util.List;

public class Algoritmo{
    static Credit datos_entrada_aux;
    public Algoritmo(Credit datosEntrada){
        datos_entrada_aux = datosEntrada;
    }
    public static List<Result> calculadora() {
        List<Result> datos = new ArrayList<>();


        //Validacion de Tasa
        double TEA = 0.0;
        if(!datos_entrada_aux.getTipoTasa().equals("TEA")){
            TEA = Math.pow((1+(datos_entrada_aux.getTasa()/100)/(360.00/datos_entrada_aux.getCapitalizacion())),(360.00/datos_entrada_aux.getCapitalizacion()))-1;
        } else TEA = datos_entrada_aux.getTasa() / 100.00;

        //Financiamiento
        double TEM = Math.pow(1.00 + TEA, datos_entrada_aux.getFreqPago() / 360.00 ) - 1.00;
        double nCuotasxAnio = 360.00 / datos_entrada_aux.getFreqPago();
        double nTotalCuotas = datos_entrada_aux.getTipoPlan();
        double cuotaInicial = datos_entrada_aux.getPrecioVentaActivo() * (datos_entrada_aux.getCuotaInicialPorcentaje() / 100.00);
        double cuotaFinal = datos_entrada_aux.getPrecioVentaActivo() * (datos_entrada_aux.getCuotaFinalPorcentaje() / 100.00);
        double montoPrestamo = datos_entrada_aux.getPrecioVentaActivo() - cuotaInicial + datos_entrada_aux.getCostesNotariales() + datos_entrada_aux.getCostesRegistrales();
        double saldoFinanciarCuotas = (montoPrestamo) - (cuotaFinal / Math.pow(1.00 + TEM + (datos_entrada_aux.getSeguroDesgravamenPorcentaje() / 100.00), nTotalCuotas + 1.00));

        //Costes y gastos periodicos
        double pSeguroDesgrav = datos_entrada_aux.getSeguroDesgravamenPorcentaje() / 100.00;
        double seguroRiesgo = (datos_entrada_aux.getSeguroRiesgoPorcentaje() / 100.00) * datos_entrada_aux.getPrecioVentaActivo() / nCuotasxAnio;

        //Indicadores de rentabilidad
        double tasaDescuentoRentabilidad = Math.pow(1 + (datos_entrada_aux.getTasaDescuentoPorcentaje() / 100.00), (datos_entrada_aux.getFreqPago() / 360.00)) - 1.00;

        System.out.println("DATOS TABLA SUPERIOR");
        System.out.println("TEA: " + TEA);
        System.out.println("TEM: " + TEM);
        System.out.println("nCuotasxAnio: " + nCuotasxAnio);
        System.out.println("nTotalCuotas: " + nTotalCuotas);
        System.out.println("cuotaInicial: " + cuotaInicial);
        System.out.println("cuotaFinal: " + cuotaFinal);
        System.out.println("montoPrestamo: " + montoPrestamo);
        System.out.println("saldoFinanciarCuotas: " + saldoFinanciarCuotas);
        System.out.println("pSeguroDesgrav: " + pSeguroDesgrav);
        System.out.println("seguroRiesgo: " + seguroRiesgo);
        System.out.println("tasaDescuentoRentabilidad: " + tasaDescuentoRentabilidad);


        // Grilla
        double saldoInicialCuotaFinal = 0.00;
        double interes = 0.00;
        double cuota = 0.00;
        double amortizacion = 0.00;
        double seguroDesgCuota = 0.00;
        double seguroRiesgoGrilla = 0.00;
        double GPS = 0.00;
        double portes = 0.00;
        double gastosAdmin = 0.00;
        double saldoFinalParaCuota = 0.00;
        double saldoInicialCuota = saldoFinanciarCuotas;
        double periodoGracia = datos_entrada_aux.getPeriodoGraciaMeses();
        double flujo = 0.00;


        for (int i = 1; i <= nTotalCuotas; i++) {
            Result datosSalida = new Result();

            if (i <= periodoGracia) {
                if (datos_entrada_aux.getTipoPeriodoGracia().equals("T")) {


                    interes = TEM * saldoInicialCuota;
                    cuota = 0.00;
                    amortizacion = 0.00;
                    seguroDesgCuota = saldoInicialCuota * (pSeguroDesgrav);
                    seguroRiesgoGrilla = seguroRiesgo;
                    GPS = datos_entrada_aux.getGPS();
                    portes = datos_entrada_aux.getPortes();
                    gastosAdmin = datos_entrada_aux.getGastosAdmin();
                    saldoFinalParaCuota = saldoInicialCuota + interes;
                    flujo = cuota + seguroDesgCuota + seguroRiesgoGrilla + GPS + portes + gastosAdmin;
                }
                else if (datos_entrada_aux.getTipoPeriodoGracia().equals("P")) {

                    interes = TEM * saldoInicialCuota;
                    cuota = interes;
                    amortizacion = 0.00;
                    seguroDesgCuota = saldoInicialCuota * (pSeguroDesgrav);
                    seguroRiesgoGrilla = seguroRiesgo;
                    GPS = datos_entrada_aux.getGPS();
                    portes = datos_entrada_aux.getPortes();
                    gastosAdmin = datos_entrada_aux.getGastosAdmin();
                    saldoFinalParaCuota = saldoInicialCuota - amortizacion;
                    flujo = cuota + seguroDesgCuota + seguroRiesgoGrilla + GPS + portes + gastosAdmin;


                }
                datosSalida.setSaldoInicialCuotaFinal(saldoInicialCuotaFinal);
                datosSalida.setInteres(interes);
                datosSalida.setCuota(cuota);
                datosSalida.setAmortizacion(amortizacion);
                datosSalida.setSeguroDesgCuota(seguroDesgCuota);
                datosSalida.setSeguroRiesgoGrilla(seguroRiesgoGrilla);
                datosSalida.setGPS(GPS);
                datosSalida.setPortes(portes);
                datosSalida.setGastosAdmin(gastosAdmin);
                datosSalida.setSaldoFinalParaCuota(saldoFinalParaCuota);
                datosSalida.setSaldoInicialCuota(saldoInicialCuota);
                datosSalida.setPeriodoGracia(periodoGracia);
                datosSalida.setFlujo(flujo);


            } else {
                interes = TEM * saldoInicialCuota;
                cuota = saldoInicialCuota * (((TEM + pSeguroDesgrav) * Math.pow(1.00 + TEM + pSeguroDesgrav, nTotalCuotas - i + 1.00)) / ((Math.pow(1.00 + TEM + pSeguroDesgrav, nTotalCuotas - i + 1.00)) - 1.00));
                seguroDesgCuota = saldoInicialCuota * (pSeguroDesgrav);
                amortizacion = cuota - interes - seguroDesgCuota;
                seguroRiesgoGrilla = seguroRiesgo;
                GPS = datos_entrada_aux.getGPS();
                portes = datos_entrada_aux.getPortes();
                gastosAdmin = datos_entrada_aux.getGastosAdmin();
                saldoFinalParaCuota = saldoInicialCuota - amortizacion;
                flujo = cuota + seguroDesgCuota + seguroRiesgoGrilla + GPS + portes + gastosAdmin;

                datosSalida.setSaldoInicialCuotaFinal(saldoInicialCuotaFinal);
                datosSalida.setInteres(interes);
                datosSalida.setCuota(cuota);
                datosSalida.setAmortizacion(amortizacion);
                datosSalida.setSeguroDesgCuota(seguroDesgCuota);
                datosSalida.setSeguroRiesgoGrilla(seguroRiesgoGrilla);
                datosSalida.setGPS(GPS);
                datosSalida.setPortes(portes);
                datosSalida.setGastosAdmin(gastosAdmin);
                datosSalida.setSaldoFinalParaCuota(saldoFinalParaCuota);
                datosSalida.setSaldoInicialCuota(saldoInicialCuota);
                datosSalida.setFlujo(flujo);
            }

            datos.add(datosSalida);

            saldoInicialCuota = saldoFinalParaCuota;


        }



        return datos;

    }
}