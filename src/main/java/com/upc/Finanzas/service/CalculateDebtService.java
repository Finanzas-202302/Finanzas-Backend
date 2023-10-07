package com.upc.Finanzas.service;

import com.upc.Finanzas.model.CalculateDebt;

public interface CalculateDebtService {
    public abstract CalculateDebt create(CalculateDebt calculateDebt);
    public abstract void delete(Long calculateDebtId);
}
