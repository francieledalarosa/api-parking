package estudoapi.apiparking.web.dto.mapper;


import estudoapi.apiparking.entity.ClienteVagas;
import estudoapi.apiparking.web.dto.EstacionamentoCreateDto;
import estudoapi.apiparking.web.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVagas toClienteVaga(EstacionamentoCreateDto dto) {
        return new ModelMapper().map(dto, ClienteVagas.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVagas clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}

