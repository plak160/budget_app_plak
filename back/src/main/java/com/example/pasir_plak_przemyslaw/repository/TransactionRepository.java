package com.example.pasir_plak_przemyslaw.repository;

import com.example.pasir_plak_przemyslaw.model.Transaction;
import com.example.pasir_plak_przemyslaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);
}