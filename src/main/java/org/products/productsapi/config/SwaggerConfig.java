package org.products.productsapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI custoOpenAPI() {
        Server httpsServer = new Server()
                .url("https://toproduct-api-production.up.railway.app");

        return new OpenAPI()
                .info(new Info()
                        .title("API de produtos")
                        .version("1.0.0")
                        .description("API para listagem e cadastro de produtos"))
                .servers(List.of(httpsServer));
    }
}
