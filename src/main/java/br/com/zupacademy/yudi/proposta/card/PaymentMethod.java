package br.com.zupacademy.yudi.proposta.card;

import java.util.stream.Stream;

public enum PaymentMethod {

    PAYPAL("paypal"),
    SAMSUNG_PAY("samsung pay");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public static PaymentMethod fromValue(String value) {
        return Stream.of(values()).filter(v -> v.name.equals(value.toLowerCase())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Must be a valid payment method."));
    }
}
