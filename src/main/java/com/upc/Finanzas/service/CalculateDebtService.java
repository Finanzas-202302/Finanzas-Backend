package com.upc.Finanzas.service;

import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.DebtResult;

public interface CalculateDebtService {
    public abstract CalculateDebt create(CalculateDebt calculateDebt);
    public abstract void delete(Long calculateDebtId);
}
