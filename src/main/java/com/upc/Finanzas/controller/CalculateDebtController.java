package com.upc.Finanzas.controller;

import com.upc.Finanzas.Algoritmo;
import com.upc.Finanzas.dto.ClientDto;
import com.upc.Finanzas.dto.EnterDataDebtDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.model.PaymentPlan;
import com.upc.Finanzas.model.User;
import com.upc.Finanzas.repository.CalculateDebtRepository;
import com.upc.Finanzas.repository.ClientRepository;
import com.upc.Finanzas.repository.UserRepository;
import com.upc.Finanzas.service.CalculateDebtService;
import com.upc.Finanzas.service.ClientService;
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
        Algoritmo algoritmo = new Algoritmo(createdDebt);
        return new ResponseEntity<>(createdDebtDto, HttpStatus.OK);
    }
    // URL: http://localhost:8080/api/bank/v1/calculate-debt/{calculateDebtId}/generate-payment-plans
// Method: POST
    @Transactional
    @PostMapping("/{calculateDebtId}/generate-payment-plans")
    public ResponseEntity<List<PaymentPlan>> generatePaymentPlans(@PathVariable(name = "calculateDebtId") Long calculateDebtId) {
        CalculateDebt calculateDebt = calculateDebtService.getById(calculateDebtId);
        existsDebtByDebtId(calculateDebtId);

        List<PaymentPlan> paymentPlans = Algoritmo.calculatePaymentPlan(calculateDebt);

        // Guardar los planes de pago en la base de datos
        List<PaymentPlan> savedPaymentPlans = paymentPlanService.guardarPlanesDePago(paymentPlans);

        return new ResponseEntity<>(savedPaymentPlans, HttpStatus.OK);
    }
    private CalculateDebt convertToEntity(EnterDataDebtDto debtDto){
        CalculateDebt debt = new CalculateDebt();
        debt.setCoin(debtDto.getCoin());
        debt.setInterest_rate(debtDto.getInterest_rate());
        debt.setInterest_rate_percentage(debtDto.getInterest_rate_percentage());
        debt.setCuota_inicial_percentage(debtDto.getCuota_inicial_percentage());
        debt.setTerm_of_loan(debtDto.getTerm_of_loan());
        debt.setCost_vehicle(debtDto.getCost_vehicle());
        debt.setPlazo_tasa_interes(debtDto.getPlazo_tasa_interes());
        debt.setSeguro_desgravamen(debtDto.getSeguro_desgravamen());
        debt.setVfmg_percentage(debtDto.getVfmg_percentage());
        debt.setCredit_percentage(debtDto.getCredit_percentage());
        Client client = clientRepository.findById(debtDto.getCliendId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente con el id " + debtDto.getCliendId()));
        debt.setClient(client);
        return debt;
    }
    private EnterDataDebtDto convertToDto(CalculateDebt calculateDebt){
        return EnterDataDebtDto.builder()
                .coin(calculateDebt.getCoin())
                .interest_rate(calculateDebt.getInterest_rate())
                .plazo_tasa_interes(calculateDebt.getPlazo_tasa_interes())
                .interest_rate_percentage(calculateDebt.getInterest_rate_percentage())
                .cuota_inicial_percentage(calculateDebt.getCuota_inicial_percentage())
                .cost_vehicle(calculateDebt.getCost_vehicle())
                .term_of_loan(calculateDebt.getTerm_of_loan())
                .seguro_desgravamen(calculateDebt.getSeguro_desgravamen())
                .vfmg_percentage(calculateDebt.getVfmg_percentage())
                .credit_percentage(calculateDebt.getCredit_percentage())
                .cliendId(calculateDebt.getClient().getId())
                .build();
    }
    private void existsDebtByDebtId(Long calculateDebtId){
        if(calculateDebtService.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe ua deuda calculada    con el id " + calculateDebtId);
        }
    }
}
