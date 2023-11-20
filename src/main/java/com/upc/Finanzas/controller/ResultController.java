package com.upc.Finanzas.controller;

import com.upc.Finanzas.dto.ResultDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.Result;
import com.upc.Finanzas.repository.CreditRepository;
import com.upc.Finanzas.repository.ResultRepository;
import com.upc.Finanzas.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bank/v1/results")
public class ResultController {
    @Autowired
    private ResultService resultService;
    private final ResultRepository resultRepository;
    private final CreditRepository creditRepository;
    public ResultController(ResultRepository resultRepository, CreditRepository creditRepository){
        this.resultRepository = resultRepository;
        this.creditRepository = creditRepository;
    }

    //URL:http://localhost:8080/api/bank/v1/results
    //Method: GET
    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> results = resultService.getAll();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{creditId}")
    public ResponseEntity<List<ResultDto>> getByCreditId(@PathVariable(name = "creditId") Long creditId){
        existsPaymentPlanByCalculateDebtId(creditId);
        List<Result> paymentPlans = resultRepository.findByCreditId(creditId);
        List<ResultDto> paymentPlansDto = paymentPlans.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(paymentPlansDto, HttpStatus.OK);
    }

    @DeleteMapping("/{creditId}")
    public ResponseEntity<Void> deletePaymentPlansByCalculateDebtId(@PathVariable(name = "creditId") Long creditId) {
        // Verificar si existe CalculateDebt con el ID proporcionado
        existsCalculateDebtById(creditId);

        // Obtener todos los PaymentPlan con el mismo CalculateDebtId
        List<Result> paymentPlans = resultRepository.findByCreditId(creditId);

        // Eliminar todos los PaymentPlan
        resultRepository.deleteAll(paymentPlans);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void existsCalculateDebtById(Long calculateDebtId){
        if(resultRepository.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe una deuda calculada con el id: " + calculateDebtId);
        }
    }
    private void existsPaymentPlanByCalculateDebtId(Long calculateDebtId){
        if(resultRepository.getById(calculateDebtId) == null){
            throw new ResourceNotFoundException("No existe una deuda calculada con el id: " + calculateDebtId);
        }
    }

    private ResultDto convertToDto(Result result){
        return ResultDto.builder()
                .saldoInicialCuotaFinal(result.getSaldoInicialCuotaFinal())
                .interes(result.getInteres())
                .cuota(result.getCuota())
                .amortizacion(result.getAmortizacion())
                .seguroDesgCuota(result.getSeguroDesgCuota())
                .seguroRiesgoGrilla(result.getSeguroRiesgoGrilla())
                .GPS(result.getGPS())
                .portes(result.getPortes())
                .gastosAdmin(result.getGastosAdmin())
                .saldoFinalParaCuota(result.getSaldoFinalParaCuota())
                .saldoInicialCuota(result.getSaldoInicialCuota())
                .periodoGracia(result.getPeriodoGracia())
                .flujo(result.getFlujo())
                .creditId(result.getCredit().getId())
                .build();
    }
}
