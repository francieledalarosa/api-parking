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
    public Usuario editarsenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){
            throw  new RuntimeException("Senha nova não confere com confirmação");
        }
        Usuario user = buscarporId(id);
        if(!user.getPassword().equals(senhaAtual)){
            throw  new RuntimeException("Senha atual não confere");
        }
        user.setPassword(novaSenha);
        return user;
    }
    @Transactional(readOnly = true)
    public List<Usuario> buscarusuarios() {
        return usuarioRepository.findAll();
    }
}
