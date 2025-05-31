package com.example.pasir_plak_przemyslaw.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double amount;


    @Enumerated(EnumType.STRING)
    private TransactionType type;


    private String tags;


    private String notes;


    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Transaction(Double amount, TransactionType type, String tags, String notes, LocalDateTime timestamp, User user) {
        this.amount = amount;
        this.type = type;
        this.tags = tags;
        this.notes = notes;
        this.timestamp = timestamp;
        this.user = user;
    }

    public Transaction(Double amount, TransactionType transactionType, String spłataDługu, @Email(message = "Enter correct e-mail") @NotBlank(message = "E-mail is required") String s, User creditor) {
    }
}
