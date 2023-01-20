package com.nemirovsky.tokenauthpaymentapiapp.repository;

import com.nemirovsky.tokenauthpaymentapiapp.model.Account;
import com.nemirovsky.tokenauthpaymentapiapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}
