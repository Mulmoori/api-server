package org.dongguk.vsa.mulmoori.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dongguk.vsa.mulmoori.core.annotation.validation.EnumValue;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue annotation;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        java.lang.Enum<?>[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues == null) {
            return false;
        }

        for (Object enumValue : enumValues) {
            if (value.toUpperCase().equals(enumValue.toString())) {
                return true;
            }
        }

        return false;
    }
}
