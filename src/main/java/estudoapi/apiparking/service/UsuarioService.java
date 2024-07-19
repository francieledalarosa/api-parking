package estudoapi.apiparking.service;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    @Transactional(readOnly = true)
    public Usuario buscarporId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }
    @Transactional
    public Usuario editarsenha(Long id, String password) {
        Usuario user = buscarporId(id);
        user.setPassword(password);
        return user;
    }
    @Transactional(readOnly = true)
    public List<Usuario> buscarusuarios() {
        return usuarioRepository.findAll();
    }
}
