package com.example.kitabhub.customannotation;

import com.example.kitabhub.dto.UserRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMismatch implements ConstraintValidator<PasswordMatches, UserRegisterDto> {

    @Override
    public boolean isValid(UserRegisterDto request, ConstraintValidatorContext context) {
        if (request == null) return true;

        String pass = request.getPassword();
        String confirm = request.getConfirmPassword();

        if (pass == null || confirm == null) return false;

        boolean matches = pass.equals(confirm);

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return matches;
    }
}
