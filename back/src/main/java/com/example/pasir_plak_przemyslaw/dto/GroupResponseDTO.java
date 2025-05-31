package com.example.pasir_plak_przemyslaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GroupResponseDTO {
    private Long id;
    private String name;
    private Long ownerId;
}
