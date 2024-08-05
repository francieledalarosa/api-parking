package estudoapi.apiparking.repository;

import estudoapi.apiparking.entity.Cliente;
import estudoapi.apiparking.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
   @Query("select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);

    Cliente findByUsuarioId(Long id);

    @Override
    Optional<Cliente> findById(Long aLong);

    Optional<Cliente> findByCpf(String cpf);
}
