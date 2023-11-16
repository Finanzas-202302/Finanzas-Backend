package com.upc.Finanzas.repository;

import com.upc.Finanzas.model.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
    boolean existsById(Long paymentPlanId);
    PaymentPlan findPaymentPlanById(Long id);
    List<PaymentPlan> findAll();
}
