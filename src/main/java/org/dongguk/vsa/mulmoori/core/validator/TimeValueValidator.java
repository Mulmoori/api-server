package org.dongguk.vsa.mulmoori.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dongguk.vsa.mulmoori.core.annotation.validation.TimeValue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValueValidator implements ConstraintValidator<TimeValue, String> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(TimeValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            LocalTime.parse(value, TIME_FORMATTER);
        } catch(DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
