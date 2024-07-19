package estudoapi.apiparking.web.controller;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.service.UsuarioService;
import estudoapi.apiparking.web.dto.UsuarioCreateDto;
import estudoapi.apiparking.web.dto.UsuarioResponseDto;
import estudoapi.apiparking.web.dto.UsuarioSenhaDto;
import estudoapi.apiparking.web.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto
            >create(@RequestBody UsuarioCreateDto usuario) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto>finduser(@PathVariable Long id) {
        Usuario user = usuarioService.buscarporId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto>updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarsenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha() );
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>>getall() {
        List<Usuario> users = usuarioService.buscarusuarios();
        return ResponseEntity.ok(users);
    }

}
