package com.upc.Finanzas.controller;

import com.upc.Finanzas.dto.DebtResultDto;
import com.upc.Finanzas.dto.EnterDataDebtDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.exception.ValidationException;
import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.repository.CalculateDebtRepository;
import com.upc.Finanzas.service.CalculateDebtService;
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
    private final CalculateDebtRepository calculateDebtRepository;

    public CalculateDebtController(CalculateDebtRepository calculateDebtRepository) {
        this.calculateDebtRepository = calculateDebtRepository;
    }

    //URL:http://localhost:8080/api/bank/v1/calculate-debt/debts
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<CalculateDebt>> getAllDebts(){
        List<CalculateDebt> debts = calculateDebtService.getAll();
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/calculate-debt/debt-data
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/debt-data/{calculateDebtId}")
    public ResponseEntity<EnterDataDebtDto> getEnterDataById(@PathVariable(name = "calculateDebtId") Long calculateDebtId){
        existsCalculateDebtById(calculateDebtId);
        CalculateDebt debt = calculateDebtService.getById(calculateDebtId);
        EnterDataDebtDto createdDebtDto = convertEnterDataToDto(debt);
        return new ResponseEntity<>(createdDebtDto, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/calculate-debt/result
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/result/{calculateDebtId}")
    public ResponseEntity<DebtResultDto> getDebtResultById(@PathVariable(name = "calculateDebtId") Long calculateDebtId){
        existsCalculateDebtById(calculateDebtId);
        CalculateDebt debt_result = calculateDebtService.getById(calculateDebtId);
        DebtResultDto debt_result_dto = convertDebtResultToDto(debt_result);
        return new ResponseEntity<>(debt_result_dto, HttpStatus.OK);
    }

    ////URL:http://localhost:8080/api/bank/v1/calculate-debt
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<EnterDataDebtDto> createDataDebt(@RequestBody EnterDataDebtDto enterDataDebtDto){
        CalculateDebt debt = convertToEntity(enterDataDebtDto);
        //validateDebt(debt)
        CalculateDebt createdDebt = calculateDebtService.create(debt);
        EnterDataDebtDto createdEnterDataDebtDto = convertEnterDataToDto(createdDebt);
        return new ResponseEntity<>(createdEnterDataDebtDto, HttpStatus.OK);
    }

    private void existsCalculateDebtById(Long calculateDebtId){
        if(calculateDebtService.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe una deuda con el id " + calculateDebtId);
        }
    }
    private EnterDataDebtDto convertEnterDataToDto(CalculateDebt calculateDebt){
        return EnterDataDebtDto.builder()
                .coin(calculateDebt.getCoin())
                .interest_rate(calculateDebt.getInterest_rate())
                .interest_rate_percentage(calculateDebt.getInterest_rate_percentage())
                .grace_period(calculateDebt.getGrace_period())
                .type_grace_period(calculateDebt.getType_grace_period())
                .cost_vehicle(calculateDebt.getCost_vehicle())
                .term_of_loan(calculateDebt.getTerm_of_loan())
                .type_of_term(calculateDebt.getType_of_term())
                .clientId(calculateDebt.getClient().getId())
                .build();
    }

    private CalculateDebt convertToEntity(EnterDataDebtDto enterDataDebtDto){
        CalculateDebt debt = new CalculateDebt();
        return debt;
    }

    private DebtResultDto convertDebtResultToDto(CalculateDebt calculateDebt){
        return DebtResultDto.builder()
                .fee_payable(calculateDebt.getFee_payable())
                .amortization(calculateDebt.getAmortization())
                .interests(calculateDebt.getInterests())
                .outstanding_debt(calculateDebt.getOutstanding_debt())
                .van(calculateDebt.getVan())
                .tir(calculateDebt.getTir())
                .clientId(calculateDebt.getClient().getId())
                .build();
    }
}
