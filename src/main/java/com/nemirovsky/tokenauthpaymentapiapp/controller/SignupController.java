package com.nemirovsky.tokenauthpaymentapiapp.controller;

import com.nemirovsky.tokenauthpaymentapiapp.model.Account;
import com.nemirovsky.tokenauthpaymentapiapp.model.User;
import com.nemirovsky.tokenauthpaymentapiapp.payload.MessageResponse;
import com.nemirovsky.tokenauthpaymentapiapp.payload.SignupRequest;
import com.nemirovsky.tokenauthpaymentapiapp.repository.AccountRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SignupController {

    private static final String INITIAL_AMOUNT = "USD 8.00";

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        Account account = accountRepository.save(new Account(user, Money.parse(INITIAL_AMOUNT)));

        return ResponseEntity.ok(new MessageResponse("User registered successfully, account created with "
                + account.getBalance() + " balance!"));

    }
}
