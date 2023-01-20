package com.nemirovsky.tokenauthpaymentapiapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Account account;

    @NotBlank
    private Money paymentAmount;

    @NotBlank
    private LocalDateTime committedTime = LocalDateTime.now();

    public Payment(User user, Account account, Money paymentAmount) {
        this.user = user;
        this.account = account;
        this.paymentAmount = paymentAmount;
    }
}
