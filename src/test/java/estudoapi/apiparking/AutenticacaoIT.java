package estudoapi.apiparking;

import estudoapi.apiparking.jwt.JwtToken;
import estudoapi.apiparking.web.dto.UsuarioLoginDto;
import estudoapi.apiparking.web.exception.ErrorMensage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutenticacaoIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void autenticar_ComCredenciaisValidas_REtornarTokenComStatus200() {
        JwtToken responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void autenticar_ComCredenciaisInvalidas_RetornarErrorMensagecomStatus400() {
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("invalid@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mariaz@gmail.com", "000000"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void autenticar_ComCredenciaisInvalidas_RetornarErrorMensagecomStatus422() {
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("maria@gmail.", "123456"))
                .exchange()
                .expectStatus().isEqualTo()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mariaz@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }
}
