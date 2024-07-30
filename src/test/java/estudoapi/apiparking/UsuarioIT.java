package estudoapi.apiparking;

import estudoapi.apiparking.web.dto.UsuarioCreateDto;
import estudoapi.apiparking.web.dto.UsuarioResponseDto;
import estudoapi.apiparking.web.dto.UsuarioSenhaDto;
import estudoapi.apiparking.web.exception.ErrorMensage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public  void creatUser_withusernameAndPasswordValids_returnUsercreatedWithstatus201(){
        UsuarioResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }
    @Test
    public  void creatUser_withusernameExists_returnErrorMesagewithStatus409(){
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joaoz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }
    @Test
    public  void creatUser_withusernameInvali_returnErrorMessageStatus422() {
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("todyy@gmail", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


    }
    @Test
    public  void creatUser_withPasswordInvali_returnErrorMessageStatus422() {
        ErrorMensage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("pedro2@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@gmail.com", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("todyy@gmail.com", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


    }
    @Test
    public  void findUser_withIdexists_returUsuarioREspondeDtoWithstatus200(){
        UsuarioResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"mariaz@gmail.com", "123456" ))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("mariaz@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/200")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"joaoz@gmail.com", "123456" ))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(200);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("joaoz@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ze@gmail.com", "123456" ))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(300);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ze@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public  void findUser_withIdUnxists_returErrorMensagetoWithstatus404(){
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }
    @Test
    public  void findUser_withClientFindAnotherClient_returErrorMensagetoWithstatus403(){
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/200")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ze@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }
    @Test
    public  void UpdatePassword_withusernameAndPasswordValids_returnUserWithstatus200(){
        UsuarioResponseDto responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/200")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joaoz@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(200);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("joaoz@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("mariaz@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
    }
    @Test
    public  void UpdatePassword_withPasswordUnvalids_returnErrorMensageWithstatus400(){
        ErrorMensage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("654321", "654321", "654321"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654323", "654321"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }
    @Test
    public  void UpdatePassword_withAnotherId_returnErrorMensageWithstatus403() {
        ErrorMensage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joaoz@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("654321", "654321", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void listUser_returnListWithStatus200() {
        List<UsuarioResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "mariaz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.size()).isGreaterThan(0);
        assertThat(responseBody.size()).isEqualTo(3);
    }

    @Test
    public void listUsers_WithnotAcess_ReturnErrorMessagewithStatus403() {
        ErrorMensage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joaoz@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}
