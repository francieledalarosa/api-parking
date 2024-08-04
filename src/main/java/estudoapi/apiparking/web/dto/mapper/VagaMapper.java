package estudoapi.apiparking.web.dto.mapper;

import estudoapi.apiparking.entity.Vaga;
import estudoapi.apiparking.web.dto.VagaCreateDto;
import estudoapi.apiparking.web.dto.VagaResponseDto;
import org.modelmapper.ModelMapper;

public class VagaMapper {

    public static Vaga toVaga (VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto (Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
