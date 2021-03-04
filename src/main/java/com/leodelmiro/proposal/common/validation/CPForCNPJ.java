package com.leodelmiro.proposal.common.validation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@CPF
@CNPJ
@Constraint(validatedBy = {})
@ConstraintComposition(CompositionType.OR)
public @interface CPForCNPJ {
    String message() default "Must be valid CPF/CNPJ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
