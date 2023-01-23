package com.nemirovsky.tokenauthpaymentapiapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.money.Money;

import java.time.LocalDateTime;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private Money balance;

    private LocalDateTime createdTime = LocalDateTime.now();

    public Account(User user, Money balance) {
        this.user = user;
        this.balance = balance;
    }
}