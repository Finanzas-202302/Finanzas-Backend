package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.model.PaymentPlan;
import com.upc.Finanzas.repository.PaymentPlanRepository;
import com.upc.Finanzas.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentPlanServiceImpl implements PaymentPlanService {
    @Autowired
    private PaymentPlanRepository paymentPlanRepository;
    @Override
    public PaymentPlan create(PaymentPlan paymentPlan) {
        return paymentPlanRepository.save(paymentPlan);
    }

    @Override
    public PaymentPlan getById(Long paymentPlanId) {
        if(paymentPlanRepository.existsById(paymentPlanId)) return paymentPlanRepository.getById(paymentPlanId);
        else return null;
    }

    @Override
    public List<PaymentPlan> getAll() {
        return paymentPlanRepository.findAll();
    }

    @Override
    public PaymentPlan update(PaymentPlan paymentPlan) {
        if(paymentPlanRepository.existsById(paymentPlan.getId())) return paymentPlanRepository.save(paymentPlan);
        else return null;
    }

    @Override
    public void delete(Long paymentPlanId) {
        paymentPlanRepository.deleteById(paymentPlanId);
    }
    @Override
    public List<PaymentPlan> guardarPlanesDePago(List<PaymentPlan> paymentPlans) {
        // Guardar cada plan de pago en la base de datos
        return paymentPlanRepository.saveAll(paymentPlans);
    }

}
