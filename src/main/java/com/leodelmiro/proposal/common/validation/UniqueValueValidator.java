package com.leodelmiro.proposal.common.validation;

import com.leodelmiro.proposal.common.exception.ApiErrorException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Object domainClass;
    private Object fieldName;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        domainClass = constraintAnnotation.domainClass().getName();
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Query query = entityManager.createQuery("SELECT 1 FROM " + domainClass + " c WHERE c." + fieldName + " = :value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();

        if (list.isEmpty()) {
            return true;
        }

        throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("Campo %s: %s %s já existe!", fieldName, fieldName, value));
    }
}
