package com.nemirovsky.tokenauthpaymentapiapp.controller;

import com.nemirovsky.tokenauthpaymentapiapp.model.Account;
import com.nemirovsky.tokenauthpaymentapiapp.model.Payment;
import com.nemirovsky.tokenauthpaymentapiapp.model.User;
import com.nemirovsky.tokenauthpaymentapiapp.repository.AccountRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.PaymentRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${tokenauthpaymentapi.paymentamount}")
    private static String paymentAmountFromProperties;
    private static final Money paymentAmount = Money.parse(paymentAmountFromProperties);

    UserRepository userRepository;

    static AccountRepository accountRepository;

    static PaymentRepository paymentRepository;

    @GetMapping("/payment")
    public String payment(Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) return "<h1>Could not find user" + principal.getName() + "in database!</h1>";

        return makePayment(user, paymentAmount);
    }

    @Transactional
    private static synchronized String makePayment(User user, Money paymentAmount) {
        Account account = accountRepository.findByUser(user).orElse(null);
        if (account == null) return "<h1>Could not find account for user" + user.getUsername() + "in database!</h1>";
        if (account.getBalance().isLessThan(paymentAmount)) return "<h1>Insufficient balance for payment</h1>";
        account.setBalance(account.getBalance().minus(paymentAmount));
        accountRepository.save(account);
        paymentRepository.save(new Payment(user, account, paymentAmount));
        return "<h1>Payment is complete! Available: $" + account.getBalance() + "</h1>";
    }
}