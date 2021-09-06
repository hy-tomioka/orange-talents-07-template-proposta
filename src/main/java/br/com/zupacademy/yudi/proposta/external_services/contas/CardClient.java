package br.com.zupacademy.yudi.proposta.external_services.contas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(url = "${external.services.url.contas}", name = "contas")
public interface CardClient {

    @PostMapping("/cartoes")
    CardResponse generate(CardRequest request);

    @PostMapping("/cartoes/{number}/bloqueios")
    CardBlockerResponse block(@PathVariable("number") String number, @RequestBody @Valid CardBlockerRequest request);

    @PostMapping("/cartoes/{number}/avisos")
    TravelNotifierResponse notifyTravel(@PathVariable("number") String number,
                                        @RequestBody @Valid TravelNotifierRequest request);
}
