package br.com.zupacademy.yudi.proposta.card;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static org.springframework.util.Assert.notNull;

@JsonAutoDetect(fieldVisibility = ANY)
public class TravelNotificationRequest {

    @NotBlank
    private String destiny;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkout;

    public TravelNotification toTravelNotification(HttpServletRequest httpRequest, Card card) {
        notNull(httpRequest, "HTTP request information are required.");
        return new TravelNotification(destiny, checkout, card,
                httpRequest.getHeader("User-Agent"),
                httpRequest.getRemoteAddr());
    }
}
