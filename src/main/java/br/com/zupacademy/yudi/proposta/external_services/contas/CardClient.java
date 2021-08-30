package br.com.zupacademy.yudi.proposta.external_services.contas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${external.services.url.contas}", name = "contas")
public interface CardClient {

    @PostMapping("/cartoes")
    CardResponse generate(CardRequest request);
}
