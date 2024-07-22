package estudoapi.apiparking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("API PARKING")
                                .description("API de gestão para estacionamento de veículos")
                                .version("v1")
                                .license(new License().name("MIT").url("https://www.mit.edu/~amini/LICENSE.md"))
                );
    }
}
