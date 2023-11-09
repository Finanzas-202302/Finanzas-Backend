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
import com.upc.Finanzas.Algoritmo;
import com.upc.Finanzas.controller.ClientController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank/v1/calculate-debt")
public class CalculateDebtController {
    Algoritmo algoritmo;
    @Autowired
    private CalculateDebtService calculateDebtService;
    @Autowired
    ClientController clientController;
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
        ResponseEntity<List<Client>> responseEntity = clientController.getAllClients();
        List<Client> clientList = responseEntity.getBody();
        CalculateDebt debt = convertToEntity(enterDataDebtDto, clientList);
        validateDebt(debt);
        CalculateDebt createdDebt = calculateDebtService.create(debt);
        EnterDataDebtDto createdEnterDataDebtDto = convertEnterDataToDto(createdDebt);
        //algoritmo.calcularCuotas(createdDebt);
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
                .cuota_inicial_percentage(calculateDebt.getCuota_inicial_percentage())
                .seguro_desgravamen(calculateDebt.getSeguro_desgravamen())
                .vfmg_percentage(calculateDebt.getVfmg_percentage())
                .credit_percentage(calculateDebt.getCredit_percentage())
                .desgravamen_tipo(calculateDebt.getDesgravamen_tipo())
                .clientId(calculateDebt.getClient().getId())
                .COK(calculateDebt.getCOK())
                .costos_iniciales(calculateDebt.getCostos_iniciales())
                .costos_periodicos(calculateDebt.getCostos_periodicos())
                .build();
    }

    private CalculateDebt convertToEntity(EnterDataDebtDto enterDataDebtDto, List<Client> clients){
        CalculateDebt debt = new CalculateDebt();
        debt.setCoin(enterDataDebtDto.getCoin());
        debt.setInterest_rate(enterDataDebtDto.getInterest_rate());
        debt.setInterest_rate_percentage(enterDataDebtDto.getInterest_rate_percentage());
        debt.setGrace_period(enterDataDebtDto.getGrace_period());
        debt.setType_grace_period(enterDataDebtDto.getType_grace_period());
        debt.setCost_vehicle(enterDataDebtDto.getCost_vehicle());
        debt.setTerm_of_loan(enterDataDebtDto.getTerm_of_loan());
        debt.setCuota_inicial_percentage(enterDataDebtDto.getCuota_inicial_percentage());
        debt.setSeguro_desgravamen(enterDataDebtDto.getSeguro_desgravamen());
        debt.setDesgravamen_tipo(enterDataDebtDto.getDesgravamen_tipo());
        debt.setVfmg_percentage(enterDataDebtDto.getVfmg_percentage());
        debt.setCredit_percentage(enterDataDebtDto.getCredit_percentage());
        debt.setCOK(enterDataDebtDto.getCOK());
        debt.setCostos_periodicos(enterDataDebtDto.getCostos_periodicos());
        debt.setCostos_iniciales(enterDataDebtDto.getCostos_iniciales());
        Long clientId = enterDataDebtDto.getClientId();
        Client client = clients.stream()
                .filter(c -> c.getId().equals(clientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clientId));
        debt.setClient(client);
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
    private void validateDebt(CalculateDebt debt){
        //validar moneda
        String coin = debt.getCoin().toLowerCase();
        if(debt.getCoin() == null || debt.getCoin().isEmpty()) throw new ValidationException("La moneda es obligatoria");
        if(!(coin.equals("soles") || coin.equals("dolares"))) throw new ValidationException("Ingrese una moneda correcta (Soles o Dolares)");
        //validar tipo de tasa de interés
        String interesRate = debt.getInterest_rate().toLowerCase();
        if(debt.getInterest_rate() == null || debt.getInterest_rate().isEmpty()) throw new ValidationException("El tipo de tasa de interés es obligatorio");
        if(!(interesRate.equals("nominal") || interesRate.equals("efectiva"))) throw new ValidationException("Ingrese un tipo de tasa de interes correcto (Nominal o Efectiva");
        //validar tasa de interés
        Double interestPercentage = debt.getInterest_rate_percentage();
        if(debt.getInterest_rate_percentage() == null) throw new ValidationException("El porcentaje de tasa de interés es obligatorio");
        if(interestPercentage <= 0) throw new ValidationException("El porcentaje de tasa de interés no puede ser menor o igual a cero");
        //validar tipo de periodo de gracia
        String typegrace = debt.getType_grace_period().toLowerCase();
        if(debt.getType_grace_period() == null || debt.getType_grace_period().isEmpty()) throw new ValidationException("El tipo de periodo de gracia es obligatorio");
        if(!(typegrace.equals("parcial") || typegrace.equals("total") || typegrace.equals("sin periodo de gracia"))) throw new ValidationException("Ingrese un tipo de periodo de gracia correcto (Total, Parcial o Sin periodo de gracia");
        //validar periodo de gracia
        Long grace = debt.getGrace_period();
        if(debt.getGrace_period() == null) throw new ValidationException("El periodo de gracia es obligatorio");
        if(grace < 0) throw new ValidationException("El periodo de gracia no puede ser menor que cero");
        //validar costo del vehículo
        Long costvehicle = debt.getCost_vehicle();
        if(debt.getCost_vehicle() == null) throw new ValidationException("El costo del vehículo es obligatorio");
        if(costvehicle <= 0) throw new ValidationException("El costo del vehículo no puede ser menor o igual a 0");
        //validar plazo del préstamo
        Long plazoprestamo = debt.getTerm_of_loan();
        if(debt.getTerm_of_loan() == null) throw new ValidationException("El plazo del préstamo es obligatorio");
        if(!(plazoprestamo == 24 || plazoprestamo == 36)) throw new ValidationException("El plazo del préstamo es de 24 o 36 cuotas");
        //validar cuota inicial
        Long cuotainicial = debt.getCuota_inicial_percentage();
        if(debt.getCuota_inicial_percentage() == null) throw new ValidationException("La cuota inicial es obligatoria");
        if(!(cuotainicial == 20 || cuotainicial == 30)) throw new ValidationException("La cuota inicial debe ser el 20% o 30% del costo del vehículo");
        //validar seguro degravamen
        Double degravamen = debt.getSeguro_desgravamen();
        if(debt.getSeguro_desgravamen() == null ) throw new ValidationException("El porcentaje de seguro degravamen es obligatorio");
        if(degravamen <= 0) throw new ValidationException("El porcentaje de seguro degravamen no puede ser menor o igual a cero");
        //validar vfmg
        Long vfmg = debt.getVfmg_percentage();
        if(debt.getVfmg_percentage() == null) throw new ValidationException("El VFMG es obligatorio");
        if(!(vfmg == 40 || vfmg == 50)) throw new ValidationException("El VFMG debe ser el 40% o 50% del costo del vehículo");
        //validar porcentaje de credito
        Long porcentaje_credito = debt.getCredit_percentage();
        if(debt.getCredit_percentage() == null) throw new ValidationException("El porcentaje del crédito es obligatorio");
        if(!(porcentaje_credito == 30 || porcentaje_credito == 40)) throw new ValidationException("El porcentaje de crédito debe ser el 30% o 40% del costo del vehículo");
        //validar suma de porcentajes
        if(vfmg + porcentaje_credito + cuotainicial != 100) throw new ValidationException("La suma de porcentajes debe ser el 100%");
        //validar tipo de tasa de desgravamen
        String desgravamentipo = debt.getDesgravamen_tipo().toLowerCase();
        if(debt.getDesgravamen_tipo() == null || debt.getDesgravamen_tipo().isEmpty()) throw new ValidationException("El tipo de tasa de interés es obligatorio");
        if(!(desgravamentipo.equals("nominal") || desgravamentipo.equals("efectiva"))) throw new ValidationException("Ingrese un tipo de tasa de interes correcto (Nominal o Efectiva");
        //validar COK
        Double cok = debt.getCOK();
        if(debt.getCOK() == null) throw new ValidationException("El COK es obligatorio");
        if(cok <= 0) throw new ValidationException("El COK no puede ser menor o igual a 0");
        //validar costos periodicos
        Double periodicos = debt.getCostos_periodicos();
        if(debt.getCostos_periodicos() == null) throw new ValidationException("Los costos periodicos son obligatorios");
        if(periodicos < 0) throw new ValidationException("Los costos periodicos no pueden ser menores que 0");
        //validar costos iniciales
        Double iniciales = debt.getCostos_iniciales();
        if(debt.getCostos_iniciales() == null ) throw new ValidationException("Los costos iniciales son obligatorios");
        if(iniciales < 0) throw new ValidationException("Los costos iniciales no pueden ser menores a 0");
    }
}
