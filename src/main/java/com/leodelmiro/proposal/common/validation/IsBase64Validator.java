package com.leodelmiro.proposal.common.validation;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsBase64Validator implements ConstraintValidator<IsBase64, String> {

    @Override
    public boolean isValid(String target, ConstraintValidatorContext constraintValidatorContext) {
        return Base64.isBase64(target);
    }
}
