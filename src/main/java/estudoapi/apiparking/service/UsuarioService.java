package estudoapi.apiparking.service;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.exception.EntityNotFoundException;
import estudoapi.apiparking.exception.IncorrectPasswordException;
import estudoapi.apiparking.exception.PasswordMismatchException;
import estudoapi.apiparking.exception.UsernameUniqueViolationException;
import estudoapi.apiparking.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Usuario salvar(Usuario usuario) {
        try{
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%S} já cadastrado!", usuario.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarporId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id = %s não encontrado.", id)
                ));
    }

    @Transactional
    public Usuario editarsenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new IncorrectPasswordException("Senha atual incorreta");
        }

        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordMismatchException("A nova senha e a confirmação não coincidem");
        }

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        return usuarioRepository.save(usuario);
    }

    
    @Transactional(readOnly = true)
    public List<Usuario> buscarusuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com username = '%s' não encontrado.", username)
                ));
    }

    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
