package estudoapi.apiparking.web.dto;

import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class UsuarioSenhaDto {
    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;
}
