package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.model.DebtResult;
import com.upc.Finanzas.repository.DebtResultRepository;
import com.upc.Finanzas.service.DebtResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtResultServiceImpl implements DebtResultService {
    @Autowired
    private DebtResultRepository debtResultRepository;
    @Override
    public DebtResult create(DebtResult debtResult){
        return debtResultRepository.save(debtResult);
    }
    @Override
    public DebtResult getById(Long debtResultId){
        return debtResultRepository.getById(debtResultId);
    }
    @Override
    public List<DebtResult> getAll(){
        return debtResultRepository.findAll();
    }
    @Override
    public DebtResult update(DebtResult debtResult){
        return debtResultRepository.save(debtResult);
    }
    @Override
    public void delete(Long debtResultId){
        debtResultRepository.deleteById(debtResultId);
    }
}
