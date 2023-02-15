package com.example.mailbe.Validation;


import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CSEDEmailValidator implements ConstraintValidator<CSEDEmail, String> {

    @Override
    public void initialize(CSEDEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return false;
        }

        String emailPatternString = "^([_A-Za-z\\d-+]+\\.?[_A-Za-z\\d-+]+@(csed.com))$";
        Pattern pattern = Pattern.compile(emailPatternString);
        return pattern.matcher(email).matches();
    }
}
