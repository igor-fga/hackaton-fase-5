package com.prontuario.pacientes.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hackaton Fase 5 API MS Gerenciador de Pacientes")
                        .version("1.0")
                        .description("Documentação da API para o projeto Hackaton Fase 5")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seuemail@dominio.com")
                                .url("https://www.fiap.com.br")
                        ));
    }
}
