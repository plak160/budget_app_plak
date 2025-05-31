package com.example.pasir_plak_przemyslaw.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

@Getter
@Setter
public class MembershipDTO {

    @NotNull(message = "e-mail nie może byc pusty")
    private String userEmail;

    @NotNull(message = "ID grupy nie moze byc puste")
    private Long groupId;
}
