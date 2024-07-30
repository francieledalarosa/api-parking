package estudoapi.apiparking;

import estudoapi.apiparking.jwt.JwtToken;
import estudoapi.apiparking.web.dto.UsuarioLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = Objects.requireNonNull(client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UsuarioLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody()).getToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer" + token);
    }
}
