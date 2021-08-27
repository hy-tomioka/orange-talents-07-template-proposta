package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.proposal.dto.NewProposalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/proposals")
public class NewProposalController {

    private final EntityManager manager;

    public NewProposalController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProposalRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Proposal proposal = request.toProposal();
        manager.persist(proposal);
        URI uri = uriComponentsBuilder.path("/api/proposals/{id}").buildAndExpand(proposal.getId()).toUri();
        // testar @UniqueValue e containizar este serviço
        return ResponseEntity.created(uri).build();
    }
}
