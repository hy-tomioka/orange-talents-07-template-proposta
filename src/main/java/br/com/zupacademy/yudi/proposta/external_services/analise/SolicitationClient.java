package br.com.zupacademy.yudi.proposta.external_services.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${external.services.url.analise}", name = "analise")
public interface SolicitationClient {

    @PostMapping("/solicitacao")
    SolicitationResponse evaluate(SolicitationRequest request);
}
