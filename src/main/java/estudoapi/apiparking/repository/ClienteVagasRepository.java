package estudoapi.apiparking.repository;

import estudoapi.apiparking.entity.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteVagasRepository extends JpaRepository<ClienteVaga, Long> {
}
