package com.nemirovsky.tokenauthpaymentapiapp.repository;

import com.nemirovsky.tokenauthpaymentapiapp.model.Account;
import com.nemirovsky.tokenauthpaymentapiapp.model.Payment;
import com.nemirovsky.tokenauthpaymentapiapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUser(User user);
}
