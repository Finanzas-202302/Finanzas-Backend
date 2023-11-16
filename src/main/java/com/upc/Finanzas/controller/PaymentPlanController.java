package com.upc.Finanzas.controller;

import com.upc.Finanzas.model.PaymentPlan;
import com.upc.Finanzas.repository.PaymentPlanRepository;
import com.upc.Finanzas.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank/v1/payment-plans")
public class PaymentPlanController {
    @Autowired
    private PaymentPlanService paymentPlanService;
    private final PaymentPlanRepository paymentPlanRepository;
    public PaymentPlanController(PaymentPlanRepository paymentPlanRepository){
        this.paymentPlanRepository = paymentPlanRepository;
    }
    //URL:http://localhost:8080/api/bank/v1/payment-plans
    //Method: GET
    @GetMapping
    public ResponseEntity<List<PaymentPlan>> getAllPaymentPlans() {
        List<PaymentPlan> paymentPlans = paymentPlanService.getAll();
        return new ResponseEntity<>(paymentPlans, HttpStatus.OK);
    }
}
