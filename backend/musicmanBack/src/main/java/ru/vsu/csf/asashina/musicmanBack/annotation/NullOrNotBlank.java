package ru.vsu.csf.asashina.musicmanBack.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlank.NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {

    String message() default "Must be null or not blank";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

    class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            return value == null || value.trim().length() > 0;
        }
    }
}
