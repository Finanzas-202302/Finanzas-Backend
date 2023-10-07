package com.upc.Finanzas.service;

import com.upc.Finanzas.model.Client;

import java.util.List;

public interface ClientService {
    public abstract Client create(Client client);
    public abstract Client getById(Long clientId);
    public abstract List<Client> getAll();
    public abstract Client update(Client client);
    public abstract void delete(Long clienteId);
}
