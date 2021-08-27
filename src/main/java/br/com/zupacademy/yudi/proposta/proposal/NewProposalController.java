package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.proposal.dto.NewProposalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/proposals")
public class NewProposalController {

    private final EntityManager manager;
    private final UniqueDocumentForProposalValidator uniqueDocumentForProposalValidator;

    public NewProposalController(EntityManager manager, UniqueDocumentForProposalValidator uniqueDocumentForProposalValidator) {
        this.manager = manager;
        this.uniqueDocumentForProposalValidator = uniqueDocumentForProposalValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(uniqueDocumentForProposalValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProposalRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Proposal proposal = request.toProposal();
        manager.persist(proposal);
        URI uri = uriComponentsBuilder.path("/api/proposals/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
