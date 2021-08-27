package br.com.zupacademy.yudi.proposta.shared.validation;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String validatedAttribute;
    private Class<?> clazz;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(UniqueValue params) {
        validatedAttribute = params.fieldName();
        clazz = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = em.createQuery("select 1 from " + clazz.getName() + " where "
                + validatedAttribute + " = :pvalue");
        query.setParameter("pvalue", value);
        List<?> resultList = query.getResultList();
        Assert.isTrue(resultList.size() <= 1, "Objeto " + clazz.getName() +
                " duplicado por conta do atributo " + validatedAttribute);
        return resultList.isEmpty();
    }
}
