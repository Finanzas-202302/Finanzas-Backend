package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.Credit;
import com.upc.Finanzas.repository.CreditRepository;
import com.upc.Finanzas.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {
    @Autowired
    private CreditRepository creditRepository;
    @Override
    public Credit create(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public Credit getById(Long creditId) {
        if(creditRepository.existsById(creditId)) return creditRepository.getById(creditId);
        else throw new ResourceNotFoundException("No existe un credito con la id: " + creditId);
    }

    @Override
    public List<Credit> getAll() {
        return creditRepository.findAll();
    }

    @Override
    public Credit update(Credit credit) {
        if(creditRepository.existsById(credit.getId())) return creditRepository.save(credit);
        else throw new ResourceNotFoundException("No existe un credito con la id: " + credit.getId());
    }

    @Override
    public void delete(Long creditId) {
        if(creditRepository.existsById(creditId)) creditRepository.deleteById(creditId);
        else throw new ResourceNotFoundException("No existe un credito con la id: " + creditId);
    }
}
