package estudoapi.apiparking.service;

import estudoapi.apiparking.entity.Cliente;
import estudoapi.apiparking.exception.CpfUniqueViolation;
import estudoapi.apiparking.exception.EntityNotFoundException;
import estudoapi.apiparking.repository.ClienteRepository;
import estudoapi.apiparking.repository.projection.ClienteProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar (Cliente cliente) {
        try{
            return clienteRepository.save(cliente);
        }catch (DataIntegrityViolationException ex) {
            throw  new CpfUniqueViolation(String.format
                    ("CPF '%s' não pode ser cadastrado, pois já existe no sistema", cliente.getCpf()));
        }
    }
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id);
    }
    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Cliente com CPF '%s' não encontrado!", cpf))
        );
    }
}
