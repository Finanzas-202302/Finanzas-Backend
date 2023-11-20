package com.upc.Finanzas.repository;

import com.upc.Finanzas.model.Credit;
import com.upc.Finanzas.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    boolean existsById(Long resultId);
    Result findResultByCreditId(Long creditId);
    List<Result> findAll();
    List<Result> findByCreditId(Long calculateDebtId);
}
