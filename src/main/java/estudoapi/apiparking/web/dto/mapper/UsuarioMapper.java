package estudoapi.apiparking.web.dto.mapper;

import estudoapi.apiparking.entity.Usuario;
import estudoapi.apiparking.web.dto.UsuarioCreateDto;
import estudoapi.apiparking.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;


public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createdto){
        return new ModelMapper().map(createdto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);

            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> todtoList(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}




