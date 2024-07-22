package estudoapi.apiparking.service;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.exception.EntityNotFoundException;
import estudoapi.apiparking.exception.UsernameUniqueViolationException;
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
        try{
            return usuarioRepository.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%S} já cadastrado!", usuario.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarporId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado.", id)
                ));
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
