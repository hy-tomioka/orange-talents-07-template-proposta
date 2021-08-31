package br.com.zupacademy.yudi.proposta.shared.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = Base64Validator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface Base64 {

    String message() default "Biometry not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
