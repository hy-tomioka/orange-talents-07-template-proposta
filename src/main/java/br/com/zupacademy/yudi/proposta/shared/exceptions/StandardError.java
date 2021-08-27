package br.com.zupacademy.yudi.proposta.shared.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class StandardError {

    private String message;

    public StandardError(String message) {
        this.message = message;
    }
}
