package estudoapi.apiparking.web.controller;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario>create(@RequestBody Usuario usuario) {
        Usuario user = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario>finduser(@PathVariable Long id) {
        Usuario user = usuarioService.buscarporId(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario>updatePassword(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario user = usuarioService.editarsenha(id, usuario.getPassword());
        return ResponseEntity.ok(user);
    }

}
