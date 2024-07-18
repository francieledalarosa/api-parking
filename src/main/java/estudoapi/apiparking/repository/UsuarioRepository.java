package estudoapi.apiparking.repository;

import estudoapi.apiparking.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}