package com.nemirovsky.tokenauthpaymentapiapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentController {
    @GetMapping("/payment")
    public String payment() {
        // TODO: payment service
        double amount = 1.11;
        return "<h1>Payment is complete! Available: $" + amount + "</h1>";
    }
}