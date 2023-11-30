package com.gamsung.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {


    private final Environment env;

    @Bean
    public OpenAPI customOpenAPI() {

        String serverUrl = "http://localhost:8080";
        if (env.getActiveProfiles().length > 0) {
            if (env.getActiveProfiles()[0].equals("prod")) {
                serverUrl = "https://api.gamsung.xyz";
            }
        }
        Server server = new Server();
        server.setUrl(serverUrl);
        List<Server> servers = List.of(server);

        return new OpenAPI()
                .info(new Info().title("TRAVELER API")
                        .version("v1")
                        .description("TRAVELER API 명세서"))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")))
                .servers(servers);

    }


}