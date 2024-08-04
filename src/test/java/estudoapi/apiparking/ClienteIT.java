package estudoapi.apiparking;

import estudoapi.apiparking.web.dto.ClienteCreateDto;
import estudoapi.apiparking.web.dto.ClienteResponseDto;
import estudoapi.apiparking.web.dto.PageableDto;
import estudoapi.apiparking.web.exception.ErrorMensage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public  void criarCliente_comEntradasValidas_retornarClientecomStatus201(){
        ClienteResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "zoy@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDto("zoy silva", "17391651010"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("zoy silva");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("17391651010");
    }

    @Test
    public  void criarCliente_comcpfJaCadastrado_retornarErrormensagecomStatus409(){
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "zoy@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDto("zoy silva", "38616418016"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void criarCliente_ComDadosInvalidos_RetornarErrorMessageStatus422() {
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "zoy@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "zoy@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDto("Bobb", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "zoy@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDto("Bobb", "911.910.640-85"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarCliente_ComUsuarioNaoPermitido_RetornarErrorMessageStatus403() {
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDto("Tobias Ferreira", "91191064085"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarCliente_ComIdExistentePeloAdmin_RetornarClienteComStatus200() {
        ClienteResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/clientes/20")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(20);
    }

    @Test
    public void buscarCliente_ComIdInexistentePeloAdmin_RetornarErrorMessageComStatus404() {
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/clientes/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarCliente_ComIdExistentePeloCliente_RetornarErrorMessageComStatus403() {
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/clientes/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joaoz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }


    @Test
    public void buscarClientes_ComPaginacaoPeloAdmin_RetornarClientesComStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/api/v1/clientes?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void buscarClientes_ComPaginacaoPeloCliente_RetornarErrorMessageComStatus403() {
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joaoz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarClienteDetails_ComDadosDoTokenDeCliente_RetornarClienteComStatus200() {
        ClienteResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/clientes/detalhes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joaoz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("38616418016");
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("joao ze");
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(10);

    }

    @Test
    public void buscarClienteDetails_ComDadosDoTokenDeAdministrador_RetornarErrorMessageComStatus403() {
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/clientes/detalhes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

}
