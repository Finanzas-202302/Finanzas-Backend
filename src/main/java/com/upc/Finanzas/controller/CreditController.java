package com.upc.Finanzas.controller;

import com.upc.Finanzas.Algoritmo;
import com.upc.Finanzas.dto.ClientDto;
import com.upc.Finanzas.dto.CreditDto;
import com.upc.Finanzas.dto.UserDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.model.Credit;
import com.upc.Finanzas.model.Result;
import com.upc.Finanzas.model.User;
import com.upc.Finanzas.repository.ClientRepository;
import com.upc.Finanzas.repository.CreditRepository;
import com.upc.Finanzas.service.CreditService;
import com.upc.Finanzas.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bank/v1/credits")
public class CreditController {
    @Autowired
    private CreditService creditService;
    @Autowired
    private ResultService resultService;
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    public CreditController(CreditRepository creditRepository, ClientRepository clientRepository){
        this.creditRepository = creditRepository;
        this.clientRepository = clientRepository;
    }

    //URL:http://localhost:8080/api/bank/v1/credits
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Credit>> getAllCredits() {
        List<Credit> credits = creditService.getAll();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/credits/{creditId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{creditId}")
    public ResponseEntity<Credit> getCreditById(@PathVariable(name = "creditId") Long creditId){
        creditRepository.existsById(creditId);
        Credit credit = creditService.getById(creditId);
        return new ResponseEntity<>(credit, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/credits
    //Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<CreditDto> createCredit(@RequestBody CreditDto creditDto){
        Credit credit = convertToEntity(creditDto);
        Credit createdCredit = creditService.create(credit);
        CreditDto createdClientDto = convertToDto(createdCredit);
        return new ResponseEntity<>(createdClientDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/bank/v1/credits/{creditId}/generate-result
// Method: POST
    @Transactional
    @PostMapping("/{creditId}/generate-result")
    public ResponseEntity<List<Result>> generateResults(@PathVariable(name = "creditId") Long creditId) {
        Credit credit = creditService.getById(creditId);
        existsCreditByCreditId(creditId);
        Algoritmo algoritmo = new Algoritmo(credit);
        List<Result> paymentPlans = algoritmo.calculadora();
        for (Result result : paymentPlans) {
            result.setCredit(credit);
        }
        // Guardar los planes de pago en la base de datos
        List<Result> savedPaymentPlans = resultService.guardarPlanesDePago(paymentPlans);
        return new ResponseEntity<>(savedPaymentPlans, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/users/{userId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/{creditId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "creditId") Long creditId){
        creditRepository.existsById(creditId);
        creditService.delete(creditId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private Credit convertToEntity(CreditDto creditDto){
        Credit credit = new Credit();
        credit.setMoneda(creditDto.getMoneda());
        credit.setPrecioVentaActivo(creditDto.getPrecioVentaActivo());
        credit.setTipoPlan(creditDto.getTipoPlan());
        credit.setCuotaInicialPorcentaje(creditDto.getCuotaInicialPorcentaje());
        credit.setCuotaFinalPorcentaje(creditDto.getCuotaFinalPorcentaje());
        credit.setCapitalizacion(creditDto.getCapitalizacion());
        credit.setPortes(creditDto.getPortes());
        credit.setTasaDescuentoPorcentaje(creditDto.getTasaDescuentoPorcentaje());
        credit.setGastosAdmin(creditDto.getGastosAdmin());
        credit.setSeguroDesgravamenPorcentaje(creditDto.getSeguroDesgravamenPorcentaje());
        credit.setSeguroRiesgoPorcentaje(creditDto.getSeguroRiesgoPorcentaje());
        credit.setTipoPeriodoGracia(creditDto.getTipoPeriodoGracia());
        credit.setPeriodoGraciaMeses(creditDto.getPeriodoGraciaMeses());
        credit.setTasa(creditDto.getTasa());
        credit.setTipoTasa(creditDto.getTipoTasa());
        credit.setNumeroAnios(creditDto.getNumeroAnios());
        credit.setTiempoDias(creditDto.getTiempoDias());
        credit.setCostesNotariales(creditDto.getCostesNotariales());
        credit.setCostesRegistrales(creditDto.getCostesRegistrales());
        credit.setGPS(creditDto.getGPS());
        credit.setFreqPago(creditDto.getFreqPago());
        Client client = clientRepository.findById(creditDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente con el id: " + creditDto.getClientId()));
        credit.setClient(client);
        return credit;
    }

    private CreditDto convertToDto(Credit credit){
        return CreditDto.builder()
                .moneda(credit.getMoneda())
                .precioVentaActivo(credit.getPrecioVentaActivo())
                .tipoPlan(credit.getTipoPlan())
                .cuotaInicialPorcentaje(credit.getCuotaInicialPorcentaje())
                .cuotaFinalPorcentaje(credit.getCuotaFinalPorcentaje())
                .capitalizacion(credit.getCapitalizacion())
                .portes(credit.getPortes())
                .tasaDescuentoPorcentaje(credit.getTasaDescuentoPorcentaje())
                .gastosAdmin(credit.getGastosAdmin())
                .seguroDesgravamenPorcentaje(credit.getSeguroDesgravamenPorcentaje())
                .seguroRiesgoPorcentaje(credit.getSeguroRiesgoPorcentaje())
                .tipoPeriodoGracia(credit.getTipoPeriodoGracia())
                .periodoGraciaMeses(credit.getPeriodoGraciaMeses())
                .cuotasPeriodoGracia(credit.getPeriodoGraciaMeses())
                .tasa(credit.getTasa())
                .tipoTasa(credit.getTipoTasa())
                .numeroAnios(credit.getNumeroAnios())
                .tiempoDias(credit.getTiempoDias())
                .costesNotariales(credit.getCostesNotariales())
                .costesRegistrales(credit.getCostesRegistrales())
                .GPS(credit.getGPS())
                .freqPago(credit.getFreqPago())
                .clientId(credit.getClient().getId())
                .build();
    }

    private void existsCreditByCreditId(Long creditId){
        if(creditService.getById(creditId) == null){
            throw new ResourceNotFoundException("No existe una deuda calculada con el id " + creditId);
        }
    }
}
