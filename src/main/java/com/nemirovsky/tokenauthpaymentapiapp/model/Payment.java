package com.nemirovsky.tokenauthpaymentapiapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.money.Money;

import java.time.LocalDateTime;

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

    private Money paymentAmount;

    private LocalDateTime committedTime = LocalDateTime.now();

    public Payment(User user, Account account, Money paymentAmount) {
        this.user = user;
        this.account = account;
        this.paymentAmount = paymentAmount;
    }
}
