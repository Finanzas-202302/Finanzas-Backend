package com.upc.Finanzas.repository;

import com.upc.Finanzas.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsById(Long clientId);
    Client findClientBydni(Long dni);
    List<Client> findAll();
}
