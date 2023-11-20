package com.upc.Finanzas.repository;

import com.upc.Finanzas.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

}
