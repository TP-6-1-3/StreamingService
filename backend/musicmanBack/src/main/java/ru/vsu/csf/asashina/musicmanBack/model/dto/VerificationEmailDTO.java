package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationEmailDTO {

    private String fullName;
    private String mainPage;
    private String verificationLink;
}
