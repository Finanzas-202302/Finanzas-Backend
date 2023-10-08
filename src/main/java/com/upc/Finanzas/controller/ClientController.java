package com.upc.Finanzas.controller;

import com.upc.Finanzas.dto.ClientDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.exception.ValidationException;
import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.model.User;
import com.upc.Finanzas.repository.ClientRepository;
import com.upc.Finanzas.repository.UserRepository;
import com.upc.Finanzas.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank/v1/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    public ClientController(ClientRepository clientRepository, UserRepository userRepository){
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }
    //URL:http://localhost:8080/api/bank/v1/clients
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(){
        List<Client> clients = clientService.getAll();
        return new ResponseEntity<List<ClientDto>>(clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/clients/{clientId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable(name = "ClientId") Long clientId){
        existsClientByClientId(clientId);
        Client client = clientService.getById(clientId);
        ClientDto clientDto = convertToDto(client);
        return new ResponseEntity<ClientDto>(clientDto, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/clients
    //Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto){
        Client client = convertToEntity(clientDto);
        validateClient(client);
        existsClientByDNI(client);
        Client createdClient = clientService.create(client);
        ClientDto createdClientDto = convertToDto(createdClient);
        return new ResponseEntity<>(createdClientDto, HttpStatus.OK);
    }

    private Client convertToEntity(ClientDto clientDto){
        Client client = new Client();
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setDni(clientDto.getDni());
        client.setVehicle(clientDto.getVehicle());

        User user = userRepository.findById(clientDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario con el id " + clientDto.getUserId()));
        client.setUser(user);

        return client;
    }
    private ClientDto convertToDto(Client client){
        return ClientDto.builder()
                .firstname(client.getFirstname())
                .lastname(client.getLastname())
                .email(client.getEmail())
                .dni(client.getDni())
                .vehicle(client.getVehicle())
                .build();
    }
    private void existsClientByClientId(Long clientId){
        if(clientService.getById(clientId) == null){
            throw new ResourceNotFoundException("No existe un cliente on el id " + clientId);
        }
    }
    private void validateClient(Client client){
        if(client.getFirstname() == null || client.getFirstname().isEmpty()){
            throw new ValidationException("El nombre del cliente es obligatorio");
        }
        if(client.getFirstname().length() > 25){
            throw new ValidationException("El nombre del cliente no debe exceder los 25 caracteres");
        }
        if(client.getLastname() == null || client.getLastname().isEmpty()){
            throw new ValidationException("El apellido del cliente es obligatorio");
        }
        if(client.getLastname().length() > 25){
            throw new ValidationException("El apellido del cliente no debe exceder los 25 caracteres");
        }
        if(client.getEmail() == null || client.getEmail().isEmpty()){
            throw new ValidationException("El correo del cliente es obligatorio");
        }
        if(client.getEmail().length() > 35){
            throw new ValidationException("El correo del cliente no debe exceder los 35 caracteres");
        }
        if(client.getDni() == null){
            throw new ValidationException("El DNI del cliente es obligatorio");
        }
        if(client.getDni() > 99999999){
            throw new ValidationException("El DNI del cliente no debe exceder los 8 caracteres");
        }
    }

    private void existsClientByDNI(Client client) {
        if (clientRepository.existsByDni(client.getDni())) {
            throw new ValidationException("Ya existe un cliente con el DNI " + client.getDni());
        }
    }
}
