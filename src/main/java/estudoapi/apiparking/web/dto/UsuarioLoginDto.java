package estudoapi.apiparking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class UsuarioLoginDto {

    @NotBlank
    @Pattern(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Formato de email inválido")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
