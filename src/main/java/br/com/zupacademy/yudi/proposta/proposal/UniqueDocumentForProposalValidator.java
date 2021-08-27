package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.proposal.dto.NewProposalRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
public class UniqueDocumentForProposalValidator implements Validator {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean supports(Class<?> aClass) {
        return NewProposalRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) return;
        NewProposalRequest request = (NewProposalRequest) o;

        List<?> matchList = manager.createQuery("select 1 from Proposal p where p.document = :requestDocument")
                .setParameter("requestDocument", request.getDocument()).getResultList();

        if (!matchList.isEmpty()) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Same proposal already registered.");
        }
    }
}
