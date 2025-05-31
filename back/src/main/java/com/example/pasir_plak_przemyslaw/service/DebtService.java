package com.example.pasir_plak_przemyslaw.service;

import com.example.pasir_plak_przemyslaw.dto.DebtDTO;
import com.example.pasir_plak_przemyslaw.model.*;
import com.example.pasir_plak_przemyslaw.repository.DebtRepository;
import com.example.pasir_plak_przemyslaw.repository.GroupRepository;
import com.example.pasir_plak_przemyslaw.repository.TransactionRepository;
import com.example.pasir_plak_przemyslaw.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtService {

    private final DebtRepository debtRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DebtService(DebtRepository debtRepository, GroupRepository groupRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.debtRepository = debtRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Debt> getGroupDebts(Long groupId) {
        return debtRepository.findByGroupId(groupId);
    }

    public Debt createDebt(DebtDTO debtDTO) {
        Group group = groupRepository.findById(debtDTO.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono grupy o id " + debtDTO.getGroupId()));

        User debtor = userRepository.findById(debtDTO.getDebtorId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono dluznika o id " + debtDTO.getDebtorId()));

        User creditor = userRepository.findById(debtDTO.getCreditorId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono wierzyciela o id " + debtDTO.getCreditorId()));

        Debt debt = new Debt();
        debt.setGroup(group);
        debt.setCreditor(creditor);
        debt.setDebtor(debtor);
        debt.setAmount(debtDTO.getAmount());
        debt.setTitle(debtDTO.getTitle());

        return debtRepository.save(debt);
    }

    public void deleteDebt(Long debtId, User currentUser) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new EntityNotFoundException("Debt not found"));

        if (!debt.getCreditor().getId().equals(currentUser.getId())) {
            throw new SecurityException("tylko wierzyciel może usunąć ten dług.");
        }

        debtRepository.delete(debt);
    }

    public boolean markedAsPaid(Long debtId, User user) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new EntityNotFoundException("Debt not found"));

        if (!debt.getDebtor().getId().equals(user.getId())) {
            throw new SecurityException("nie jesteś dłużnikiem.");
        }

        debt.setMarkedAsPaid(true);
        debtRepository.save(debt);

        return true;
    }

    public boolean confirmedPayment(Long debtId, User user) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new EntityNotFoundException("Debt not found"));
        if (!debt.getCreditor().getId().equals(user.getId())) {
            throw new SecurityException("nie jestes wierzycielem.");
        }

        if(!debt.isMarkedAsPaid()) {
            throw new IllegalStateException("dłużnik jeszcze nie oznaczył jako opłacone");
        }

        debt.setConfirmedByCreditor(true);
        debtRepository.save(debt);

        Transaction incomeTx = new Transaction(
                debt.getAmount(),
                TransactionType.INCOME,
                "spłata długu",
                "spłata długu od: "+ debt.getDebtor().getEmail(),
                debt.getCreditor()
        );
        transactionRepository.save(incomeTx);

        Transaction expenseTx = new Transaction(
                debt.getAmount(),
                TransactionType.EXPENSE,
                "spłata długu",
                "spłacono długu dla: "+ debt.getCreditor().getEmail(),
                debt.getDebtor()
        );
        transactionRepository.save(expenseTx);
        return true;
    }
}
