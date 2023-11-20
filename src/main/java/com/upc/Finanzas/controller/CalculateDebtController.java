package com.upc.Finanzas.controller;

import com.upc.Finanzas.Algoritmo;
import com.upc.Finanzas.dto.EnterDataDebtDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.model.PaymentPlan;
import com.upc.Finanzas.repository.CalculateDebtRepository;
import com.upc.Finanzas.repository.ClientRepository;
import com.upc.Finanzas.service.CalculateDebtService;
import com.upc.Finanzas.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank/v1/calculate-debt")
public class CalculateDebtController {
    @Autowired
    private CalculateDebtService calculateDebtService;
    @Autowired
    private PaymentPlanService paymentPlanService;
    private final CalculateDebtRepository calculateDebtRepository;
    private final ClientRepository clientRepository;
    public CalculateDebtController(CalculateDebtRepository calculateDebtRepository, ClientRepository clientRepository){
        this.calculateDebtRepository = calculateDebtRepository;
        this.clientRepository = clientRepository;
    }
    //URL:http://localhost:8080/api/bank/v1/calculate-debt
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<CalculateDebt>> getAllDebts(){
        List<CalculateDebt> calculateDebts = calculateDebtService.getAll();
        return new ResponseEntity<>(calculateDebts, HttpStatus.OK);
    }
    //URL:http://localhost:8080/api/bank/v1/calculate-debt/{calculateDebtId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{calculateDebtId}")
    public ResponseEntity<CalculateDebt> getDebtById(@PathVariable(name = "calculateDebtId") Long calculateDebtId){
        existsDebtByDebtId(calculateDebtId);
        CalculateDebt debt = calculateDebtService.getById(calculateDebtId);
        return new ResponseEntity<>(debt, HttpStatus.OK);
    }
    //URL:http://localhost:8080/api/bank/v1/calculate
    //Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<EnterDataDebtDto> createDebt(@RequestBody EnterDataDebtDto enterDataDebtDto){
        CalculateDebt debt = convertToEntity(enterDataDebtDto);
        //validateClient(debt);
        CalculateDebt createdDebt = calculateDebtService.create(debt);
        EnterDataDebtDto createdDebtDto = convertToDto(createdDebt);
        return new ResponseEntity<>(createdDebtDto, HttpStatus.OK);
    }
    // URL: http://localhost:8080/api/bank/v1/calculate-debt/{calculateDebtId}/generate-payment-plans
// Method: POST
    @Transactional
    @PostMapping("/{calculateDebtId}/generate-payment-plans")
    public ResponseEntity<List<PaymentPlan>> generatePaymentPlans(@PathVariable(name = "calculateDebtId") Long calculateDebtId) {
        CalculateDebt calculateDebt = calculateDebtService.getById(calculateDebtId);
        existsDebtByDebtId(calculateDebtId);
        Algoritmo algoritmo = new Algoritmo(calculateDebt);
        List<PaymentPlan> paymentPlans = algoritmo.calculatePaymentPlan(calculateDebt);

        // Guardar los planes de pago en la base de datos
        List<PaymentPlan> savedPaymentPlans = paymentPlanService.guardarPlanesDePago(paymentPlans);

        return new ResponseEntity<>(savedPaymentPlans, HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping("/{calculateDebtId}")
    public ResponseEntity<String> deleteDebt(@PathVariable(name = "calculateDebtId") Long calculateDebtId) {
        existsDebtByDebtId(calculateDebtId);
        calculateDebtService.delete(calculateDebtId);
        return new ResponseEntity<>("La deuda calculada con ID " + calculateDebtId + " ha sido eliminada correctamente.", HttpStatus.OK);
    }
    private CalculateDebt convertToEntity(EnterDataDebtDto debtDto){
        CalculateDebt debt = new CalculateDebt();
        debt.setCoin(debtDto.getCoin());
        debt.setInterest_rate(debtDto.getInterest_rate());
        debt.setInterest_rate_percentage(debtDto.getInterest_rate_percentage());
        debt.setPeriodo_de_capitalizacion(debtDto.getPeriodo_de_capitalizacion());
        debt.setCapitalizacion_especial(debtDto.getCapitalizacion_especial());
        debt.setPlazo_interes_especial(debtDto.getPlazo_interes_especial());
        debt.setType_grace_period(debtDto.getType_grace_period());
        debt.setGrace_period(debtDto.getGrace_period());
        debt.setCuota_inicial_percentage(debtDto.getCuota_inicial_percentage());
        debt.setTerm_of_loan(debtDto.getTerm_of_loan());
        debt.setCost_vehicle(debtDto.getCost_vehicle());
        debt.setPlazo_tasa_interes(debtDto.getPlazo_tasa_interes());
        debt.setSeguro_desgravamen(debtDto.getSeguro_desgravamen());
        debt.setVfmg_percentage(debtDto.getVfmg_percentage());
        debt.setCredit_percentage(debtDto.getCredit_percentage());
        debt.setCostos_notariales(debtDto.getCostos_notariales());
        debt.setCostos_notariales_bool(debtDto.getCostos_notariales_bool());
        debt.setCostos_registrales(debtDto.getCostos_registrales());
        debt.setCostos_registrales_bool(debtDto.getCostos_registrales_bool());
        debt.setTasacion(debtDto.getTasacion());
        debt.setTasacion_bool(debtDto.getTasacion_bool());
        debt.setEstudio_de_titulos(debtDto.getEstudio_de_titulos());
        debt.setEstudio_de_titulos_bool(debtDto.getEstudio_de_titulos_bool());
        debt.setOtros_costes(debtDto.getOtros_costes());
        debt.setOtros_costes_bool(debtDto.getOtros_costes_bool());
        debt.setPortes(debtDto.getPortes());
        debt.setGastos_administrativos(debtDto.getGastos_administrativos());
        debt.setComision(debtDto.getComision());
        debt.setPenalidad(debtDto.getPenalidad());
        debt.setComunicacion(debtDto.getComunicacion());
        debt.setSeguridad(debtDto.getSeguridad());
        debt.setVAN(debtDto.getVAN());
        debt.setFecha_prestamo(debtDto.getFecha_prestamo());
        debt.setOtros(debtDto.getOtros());
        debt.setCOK(debtDto.getCok());
        Client client = clientRepository.findById(debtDto.getCliendId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente con el id " + debtDto.getCliendId()));
        debt.setClient(client);
        return debt;
    }
    private EnterDataDebtDto convertToDto(CalculateDebt calculateDebt){
        return EnterDataDebtDto.builder()
                .coin(calculateDebt.getCoin())
                .interest_rate(calculateDebt.getInterest_rate())
                .periodo_de_capitalizacion(calculateDebt.getPeriodo_de_capitalizacion())
                .capitalizacion_especial(calculateDebt.getCapitalizacion_especial())
                .plazo_tasa_interes(calculateDebt.getPlazo_tasa_interes())
                .fecha_prestamo(calculateDebt.getFecha_prestamo())
                .plazo_interes_especial(calculateDebt.getPlazo_interes_especial())
                .interest_rate_percentage(calculateDebt.getInterest_rate_percentage())
                .cuota_inicial_percentage(calculateDebt.getCuota_inicial_percentage())
                .cost_vehicle(calculateDebt.getCost_vehicle())
                .term_of_loan(calculateDebt.getTerm_of_loan())
                .seguro_desgravamen(calculateDebt.getSeguro_desgravamen())
                .vfmg_percentage(calculateDebt.getVfmg_percentage())
                .credit_percentage(calculateDebt.getCredit_percentage())
                .type_grace_period(calculateDebt.getType_grace_period())
                .grace_period(calculateDebt.getGrace_period())
                .cliendId(calculateDebt.getClient().getId())
                .portes(calculateDebt.getPortes())
                .gastos_administrativos(calculateDebt.getGastos_administrativos())
                .comision(calculateDebt.getComision())
                .penalidad(calculateDebt.getPenalidad())
                .comunicacion(calculateDebt.getComunicacion())
                .seguridad(calculateDebt.getSeguridad())
                .costos_notariales(calculateDebt.getCostos_notariales())
                .costos_notariales_bool(calculateDebt.getCostos_notariales_bool())
                .costos_registrales(calculateDebt.getCostos_registrales())
                .costos_registrales_bool(calculateDebt.getCostos_registrales_bool())
                .tasacion(calculateDebt.getTasacion())
                .tasacion_bool(calculateDebt.getTasacion_bool())
                .estudio_de_titulos(calculateDebt.getEstudio_de_titulos())
                .estudio_de_titulos_bool(calculateDebt.getEstudio_de_titulos_bool())
                .otros_costes(calculateDebt.getOtros_costes())
                .otros_costes_bool(calculateDebt.getOtros_costes_bool())
                .cok(calculateDebt.getCOK())
                .VAN(calculateDebt.getVAN())
                .cok(calculateDebt.getCOK())
                .otros(calculateDebt.getOtros())
                .build();
    }
    private void existsDebtByDebtId(Long calculateDebtId){
        if(calculateDebtService.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe una deuda calculada con el id " + calculateDebtId);
        }
    }
}
