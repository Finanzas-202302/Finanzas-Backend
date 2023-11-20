package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.model.Result;
import com.upc.Finanzas.repository.ResultRepository;
import com.upc.Finanzas.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Override
    public Result create(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public Result getById(Long resultId) {
        if(resultRepository.existsById(resultId)) return resultRepository.getById(resultId);
        else throw new ResourceNotFoundException("No existe un credito con la id: " + resultId);
    }

    @Override
    public List<Result> getAll() {
        return resultRepository.findAll();
    }

    @Override
    public Result update(Result result) {
        if(resultRepository.existsById(result.getId())) return resultRepository.save(result);
        else throw new ResourceNotFoundException("No existe un credito con la id: " + result.getId());
    }

    @Override
    public void delete(Long resultId) {
        resultRepository.deleteById(resultId);
    }
    @Override
    public List<Result> guardarPlanesDePago(List<Result> paymentPlans) {
        // Guardar cada plan de pago en la base de datos
        return resultRepository.saveAll(paymentPlans);
    }
}
