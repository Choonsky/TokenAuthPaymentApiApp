package com.nemirovsky.tokenauthpaymentapiapp.controller;

import com.nemirovsky.tokenauthpaymentapiapp.model.Account;
import com.nemirovsky.tokenauthpaymentapiapp.model.Payment;
import com.nemirovsky.tokenauthpaymentapiapp.model.User;
import com.nemirovsky.tokenauthpaymentapiapp.repository.AccountRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.PaymentRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentController {

    private static final String PAYMENT_AMOUNT = "USD 1.10";

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping("/payment")
    public String payment(Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) return "Could not find user" + principal.getName() + "in database!";

        return makePayment(user, Money.parse(PAYMENT_AMOUNT));
    }

    @Transactional
    private synchronized String makePayment(User user, Money paymentAmount) {
        Account account = accountRepository.findByUser(user).orElse(null);
        if (account == null) return "Could not find account for user" + user.getUsername() + "in database!";
        if (account.getBalance().isLessThan(paymentAmount))
            return "Insufficient balance for payment. Available: $" + account.getBalance();
        account.setBalance(account.getBalance().minus(paymentAmount));
        accountRepository.save(account);
        paymentRepository.save(new Payment(user, account, paymentAmount));
        return "Payment is complete! Available: $" + account.getBalance();
    }
}