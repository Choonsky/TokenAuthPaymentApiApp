package com.nemirovsky.tokenauthpaymentapiapp.controller;

import com.nemirovsky.tokenauthpaymentapiapp.model.Payment;
import com.nemirovsky.tokenauthpaymentapiapp.repository.AccountRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.PaymentRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${tokenauthpaymentapi.paymentamount}")
    private static String paymentAmountFromProperties;
    private static final Money paymentAmount = Money.parse(paymentAmountFromProperties);

    AccountRepository accountRepository;

    PaymentRepository paymentRepository;

    @GetMapping("/payment")
    public String payment() {

        // TODO: take a user from token

        // TODO: check if balance if enough (return if not)

        // TODO: Transactional decreasing a balance and writing a payment
        // account.setBalance(account.getBalance().minus(paymentAmount));
        // accountRepository.save(account);
        // paymentRepository.save(new Payment(user, account, paymentAmount));
        // String result = account.getBalance();

        String result = "USD 0.01";

        return "<h1>Payment is complete! Available: $" + result + "</h1>";
    }
}