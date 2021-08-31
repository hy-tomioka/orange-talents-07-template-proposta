package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.proposal.dto.ProposalStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.UUID;

@RestController
@RequestMapping("/api/proposals")
public class ProposalStatusController {

    private final EntityManager manager;
    private Logger LOG = LoggerFactory.getLogger(ProposalStatusController.class);

    public ProposalStatusController(EntityManager manager) {
        this.manager = manager;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProposalStatusResponse> consult(@PathVariable("uuid") UUID uuid) {
        LOG.info("Searching for proposal = {}", uuid);
        Proposal proposal = manager.createQuery("select p from Proposal p where uuid = :puuid", Proposal.class)
                .setParameter("puuid", uuid).getResultStream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposal must exist."));
        LOG.info("Proposal = {} found.", uuid);
        return ResponseEntity.ok(new ProposalStatusResponse(proposal));
    }
}
