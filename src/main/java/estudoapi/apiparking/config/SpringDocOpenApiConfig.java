package estudoapi.apiparking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(
                        new Info()
                                .title("API PARKING")
                                .description("API de gestão para estacionamento de veículos")
                                .version("v1")
                                .license(new License().name("MIT").url("https://www.mit.edu/~amini/LICENSE.md"))
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um token válido para prosseguir!")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
