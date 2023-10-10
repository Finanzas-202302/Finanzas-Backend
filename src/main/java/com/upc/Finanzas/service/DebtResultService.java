package com.upc.Finanzas.service;


import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.DebtResult;

import java.util.List;

public interface DebtResultService {
    public abstract DebtResult create(DebtResult debtResult);
    //public abstract DebtResult calculateDebt(CalculateDebt calculateDebt);
    public abstract DebtResult getById(Long debtResultId);
    public abstract List<DebtResult> getAll();
    public abstract DebtResult update(DebtResult debtResult);
    public abstract void delete(Long debtResultId);
}
