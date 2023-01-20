package com.nemirovsky.tokenauthpaymentapiapp.controller;

import com.nemirovsky.tokenauthpaymentapiapp.model.Account;
import com.nemirovsky.tokenauthpaymentapiapp.model.Role;
import com.nemirovsky.tokenauthpaymentapiapp.model.Roles;
import com.nemirovsky.tokenauthpaymentapiapp.model.User;
import com.nemirovsky.tokenauthpaymentapiapp.payload.MessageResponse;
import com.nemirovsky.tokenauthpaymentapiapp.payload.SignupRequest;
import com.nemirovsky.tokenauthpaymentapiapp.repository.AccountRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.RoleRepository;
import com.nemirovsky.tokenauthpaymentapiapp.repository.UserRepository;
import com.nemirovsky.tokenauthpaymentapiapp.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupController {

    @Value("${tokenauthpaymentapi.initialamount}")
    private static String initialAmountFromProperties;
    private static final Money initialAmount = Money.parse(initialAmountFromProperties);

    UserRepository userRepository;

    AccountRepository accountRepository;

    RoleRepository roleRepository;

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

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN" -> {
                        Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "MODERATOR" -> {
                        Role modRole = roleRepository.findByName(Roles.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        accountRepository.save(new Account(user, initialAmount));

        return ResponseEntity.ok(new MessageResponse("User registered successfully, account created with "
                + initialAmountFromProperties + "balance!"));

    }
}
