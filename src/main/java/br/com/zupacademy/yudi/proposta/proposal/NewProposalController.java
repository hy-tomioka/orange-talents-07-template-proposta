package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.external_services.analise.SolicitationClient;
import br.com.zupacademy.yudi.proposta.external_services.analise.SolicitationRequest;
import br.com.zupacademy.yudi.proposta.proposal.dto.NewProposalRequest;
import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.util.Assert.notNull;

@RestController
@RequestMapping("/api/proposals")
public class NewProposalController {

    private final Logger LOG = LoggerFactory.getLogger(NewProposalController.class);

    private final UniqueDocumentForProposalValidator uniqueDocumentForProposalValidator;
    private final TransactionRunner transactionRunner;

    @Autowired
    private SolicitationClient solicitationClient;

    public NewProposalController(TransactionRunner transactionRunner, UniqueDocumentForProposalValidator uniqueDocumentForProposalValidator, SolicitationClient solicitationClient) {
        this.transactionRunner = transactionRunner;
        this.uniqueDocumentForProposalValidator = uniqueDocumentForProposalValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(uniqueDocumentForProposalValidator);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid NewProposalRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Proposal proposal = request.toProposal();
        transactionRunner.saveAndCommit(proposal);

        String solicitationResult = getAnalysisResult(proposal);
        proposal.setStatus(ProposalStatus.fromValue(solicitationResult));
        transactionRunner.updateAndCommit(proposal);

        URI uri = uriComponentsBuilder.path("/api/proposals/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private String getAnalysisResult(Proposal proposal) {
        notNull(proposal, "Proposal must not be null.");
        return sendRequestToAnalise(proposal);
    }

    private String sendRequestToAnalise(Proposal proposal) {
        LOG.info("Sending resquest to Analie service for document {}", proposal.getDocument());
        String solicitationResult;
        try {
            solicitationResult = solicitationClient.evaluate(new SolicitationRequest(proposal))
                    .getResultadoSolicitacao();
            LOG.info("Request sent to Analise service for document {}", proposal.getDocument());
        } catch (FeignException exception) {
            solicitationResult = "COM_RESTRICAO";
            LOG.info("Proposal analysis returned 422 UnprocessedEntity for document = {}", proposal.getDocument());
        }
        return solicitationResult;
    }
}
