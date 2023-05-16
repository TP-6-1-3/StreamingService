package ru.vsu.csf.asashina.musicmanBack.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplate implements Template {

    VERIFICATION_USER("email/Verification");

    private final String name;
}
