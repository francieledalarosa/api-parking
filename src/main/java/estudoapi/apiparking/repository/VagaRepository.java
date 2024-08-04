package estudoapi.apiparking.repository;

import estudoapi.apiparking.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
}
