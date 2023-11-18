package com.upc.Finanzas.service;

import com.upc.Finanzas.model.Client;
import com.upc.Finanzas.model.PaymentPlan;

import java.util.List;

public interface PaymentPlanService {
    public abstract PaymentPlan create(PaymentPlan paymentPlan);
    public abstract PaymentPlan getById(Long paymentPlanId);
    public abstract List<PaymentPlan> getAll();
    public abstract PaymentPlan update(PaymentPlan paymentPlan);
    public abstract void delete(Long paymentPlanId);
    public abstract List<PaymentPlan> guardarPlanesDePago(List<PaymentPlan> paymentPlans);
    public abstract List<PaymentPlan> getByCalculateDebtId(Long calculateDebtId);
}
