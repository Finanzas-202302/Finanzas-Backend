package com.upc.Finanzas.controller;

import com.upc.Finanzas.dto.PaymentPlanDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.PaymentPlan;
import com.upc.Finanzas.repository.PaymentPlanRepository;
import com.upc.Finanzas.service.CalculateDebtService;
import com.upc.Finanzas.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank/v1/payment-plans")
public class PaymentPlanController {
    @Autowired
    private PaymentPlanService paymentPlanService;
    @Autowired
    private CalculateDebtService calculateDebtService;
    private final PaymentPlanRepository paymentPlanRepository;
    public PaymentPlanController(PaymentPlanRepository paymentPlanRepository){
        this.paymentPlanRepository = paymentPlanRepository;
    }
    //URL:http://localhost:8080/api/bank/v1/payment-plans
    //Method: GET
    @GetMapping
    public ResponseEntity<List<PaymentPlan>> getAllPaymentPlans() {
        List<PaymentPlan> paymentPlans = paymentPlanService.getAll();
        return new ResponseEntity<>(paymentPlans, HttpStatus.OK);
    }

    @GetMapping("/{calculateDebtId}")
    public ResponseEntity<List<PaymentPlanDto>> getByCalculateDebtId(@PathVariable(name = "calculateDebtId") Long calculateDebtId){
        existsPaymentPlanByCalculateDebtId(calculateDebtId);
        List<PaymentPlan> paymentPlans = paymentPlanService.getByCalculateDebtId(calculateDebtId);
        List<PaymentPlanDto> paymentPlansDto = paymentPlans.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(paymentPlansDto, HttpStatus.OK);
    }

    @DeleteMapping("/{calculateDebtId}")
    public ResponseEntity<Void> deletePaymentPlansByCalculateDebtId(@PathVariable(name = "calculateDebtId") Long calculateDebtId) {
        // Verificar si existe CalculateDebt con el ID proporcionado
        existsCalculateDebtById(calculateDebtId);

        // Obtener todos los PaymentPlan con el mismo CalculateDebtId
        List<PaymentPlan> paymentPlans = paymentPlanRepository.findByCalculateDebtId(calculateDebtId);

        // Eliminar todos los PaymentPlan
        paymentPlanRepository.deleteAll(paymentPlans);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void existsCalculateDebtById(Long calculateDebtId){
        if(calculateDebtService.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe una deuda calculada con el id: " + calculateDebtId);
        }
    }
    private void existsPaymentPlanByCalculateDebtId(Long calculateDebtId){
        if(calculateDebtService.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe una deuda calculada con el id: " + calculateDebtId);
        }
    }

    private PaymentPlanDto convertToDto(PaymentPlan paymentPlan){
        return PaymentPlanDto.builder()
                .periodNumber(paymentPlan.getPeriodNumber())
                .dueDate(paymentPlan.getDueDate())
                .prestamo(paymentPlan.getPrestamo())
                .financiamiento(paymentPlan.getFinanciamiento())
                .flujo_total(paymentPlan.getFlujo_total())
                .interes(paymentPlan.getInteres())
                .cuota_financiamiento(paymentPlan.getCuota_financiamiento())
                .amortizacion(paymentPlan.getAmortizacion())
                .seguro_desgravamen(paymentPlan.getSeguro_desgravamen())
                .portes(paymentPlan.getPortes())
                .gastos_administrativos(paymentPlan.getGastos_administrativos())
                .comision(paymentPlan.getComision())
                .penalidad(paymentPlan.getPenalidad())
                .comunicacion(paymentPlan.getComunicacion())
                .seguridad(paymentPlan.getSeguridad())
                .otros(paymentPlan.getOtros())
                .CalculateDebtId(paymentPlan.getCalculateDebt().getId())
                .build();
    }
}
