package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.repository.CalculateDebtRepository;
import com.upc.Finanzas.service.CalculateDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateDebtServiceImpl implements CalculateDebtService {
    @Autowired
    private CalculateDebtRepository calculateDebtRepository;
    @Override
    public CalculateDebt create(CalculateDebt calculateDebt){
       return calculateDebtRepository.save(calculateDebt);
    }
    @Override
    public void delete(Long calculateDebtId){
        calculateDebtRepository.deleteById(calculateDebtId);
    }
    @Override
    public CalculateDebt getById(Long calculateDebtId){
        if(calculateDebtRepository.existsById(calculateDebtId)) return calculateDebtRepository.getById(calculateDebtId);
        else return null;
    }
    @Override
    public List<CalculateDebt> getAll(){
        return calculateDebtRepository.findAll();
    }
    @Override
    public CalculateDebt update(CalculateDebt calculateDebt){
        if(calculateDebtRepository.existsById(calculateDebt.getId())) return calculateDebtRepository.save(calculateDebt);
        else return null;
    }
}
