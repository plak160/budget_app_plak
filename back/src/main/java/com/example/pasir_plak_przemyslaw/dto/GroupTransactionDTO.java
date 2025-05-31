package com.example.pasir_plak_przemyslaw.dto;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupTransactionDTO {
    private Long groupId;
    private Double amount;
    private String title;
    private String type;
    private List<Long> selectedUserIds;
}
