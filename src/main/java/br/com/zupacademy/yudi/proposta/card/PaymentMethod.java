package br.com.zupacademy.yudi.proposta.card;

import java.util.stream.Stream;

public enum PaymentMethod {

    PAYPAL,
    SAMSUNG_PAY;

    public static PaymentMethod fromValue(String value) {
        return Stream.of(values()).filter(v -> v.name().equals(value.toUpperCase())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Must be a valid payment gateway."));
    }
}
