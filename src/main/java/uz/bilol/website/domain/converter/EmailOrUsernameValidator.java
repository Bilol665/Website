package uz.bilol.website.domain.converter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uz.bilol.website.domain.dto.user.LoginDto;

public class EmailOrUsernameValidator implements ConstraintValidator<EmailOrUsername, LoginDto> {

    @Override
    public boolean isValid(LoginDto loginDto, ConstraintValidatorContext context) {
        if (loginDto == null) {
            return false;
        }
        return (loginDto.getEmail() != null && !loginDto.getEmail().isEmpty()) ||
                (loginDto.getUsername() != null && !loginDto.getUsername().isEmpty());
    }
}
