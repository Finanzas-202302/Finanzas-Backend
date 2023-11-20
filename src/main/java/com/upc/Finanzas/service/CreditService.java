package com.upc.Finanzas.service;

import com.upc.Finanzas.model.Credit;

import java.util.List;

public interface CreditService {
    public abstract Credit create(Credit credit);
    public abstract Credit getById(Long creditId);
    public abstract List<Credit> getAll();
    public abstract Credit update(Credit credit);
    public abstract void delete(Long creditId);
}
