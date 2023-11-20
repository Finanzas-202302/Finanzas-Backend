package com.upc.Finanzas.service;

import com.upc.Finanzas.model.Result;

import java.util.List;

public interface ResultService {
    public abstract Result create(Result result);
    public abstract Result getById(Long resultId);
    public abstract List<Result> getAll();
    public abstract Result update(Result result);
    public abstract void delete(Long resultId);
    public abstract List<Result> guardarPlanesDePago(List<Result> results);
}
