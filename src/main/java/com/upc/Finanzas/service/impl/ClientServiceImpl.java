package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.repository.ClientRepository;
import com.upc.Finanzas.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public Client create(Client client){
        //validaciones
        return clientRepository.save(client);
    }
    @Override
    public Client getById(Long clientId){
        if(clientRepository.existsById(clientId)) return clientRepository.getById(clientId);
        else return null;
    }
    @Override
    public List<Client> getAll(){
        return clientRepository.findAll();
    }
    @Override
    public Client update(Client client){
        if(clientRepository.existsById(client.getId())) return clientRepository.save(client);
        else return null;
    }
    @Override
    public void delete(Long clientId){
        clientRepository.deleteById(clientId);
    }
}
