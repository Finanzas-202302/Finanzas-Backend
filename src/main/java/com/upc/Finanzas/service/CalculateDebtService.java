package com.upc.Finanzas.service;

import com.upc.Finanzas.model.CalculateDebt;

import java.util.List;

public interface CalculateDebtService {
    public abstract CalculateDebt create(CalculateDebt calculateDebt);
    public abstract void delete(Long calculateDebtId);
    public abstract List<CalculateDebt> getAll();
    public abstract CalculateDebt update(CalculateDebt calculateDebt);
    public abstract CalculateDebt getById(Long calculateDebtId);
}
