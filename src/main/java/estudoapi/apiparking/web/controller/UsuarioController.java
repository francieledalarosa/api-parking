package estudoapi.apiparking.web.controller;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.service.UsuarioService;
import estudoapi.apiparking.web.dto.UsuarioCreateDto;
import estudoapi.apiparking.web.dto.UsuarioResponseDto;
import estudoapi.apiparking.web.dto.UsuarioSenhaDto;
import estudoapi.apiparking.web.dto.mapper.UsuarioMapper;
import estudoapi.apiparking.web.exception.ErrorMensage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name= "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    @Operation(summary = "Criar novo usuário",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "E-mail já cadastrado no sistema!",
                content = @Content(mediaType = "application/json",  schema = @Schema(implementation = ErrorMensage.class))),
                @ApiResponse(responseCode = "422", description = "Não processado por entrada de dados inválidos!",
                        content = @Content(mediaType = "application/json",  schema = @Schema(implementation = ErrorMensage.class)))
        })

    @PostMapping
    public ResponseEntity<UsuarioResponseDto
            >create(@Valid @RequestBody UsuarioCreateDto usuario) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }
    @Operation(summary = "Buscar usuário por id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado!",
                            content = @Content(mediaType = "application/json",  schema = @Schema(implementation = ErrorMensage.class)))
            })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto>finduser(@PathVariable Long id) {
        Usuario user = usuarioService.buscarporId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Alterar senha do usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere!",
                            content = @Content(mediaType = "application/json",  schema = @Schema(implementation = ErrorMensage.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado!",
                            content = @Content(mediaType = "application/json",  schema = @Schema(implementation = ErrorMensage.class)))
            })

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto>updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarsenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha() );
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }
    @Operation(summary = "Buscar lista de usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "200", description = "Lista vazia!",
                            content = @Content(mediaType = "application/json",  schema = @Schema(implementation = ErrorMensage.class)))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>>getall() {
        List<Usuario> users = usuarioService.buscarusuarios();
        return ResponseEntity.ok(UsuarioMapper.todtoList(users));
    }

}
