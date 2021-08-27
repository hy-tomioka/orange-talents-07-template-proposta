package br.com.zupacademy.yudi.proposta.shared.validation;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

@CPF
@CNPJ
@ConstraintComposition(OR)
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface CpfOrCnpj {

    String message() default "Não é um CPF ou CNPJ válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
