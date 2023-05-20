package ru.vsu.csf.asashina.musicmanBack.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@OpenAPIDefinition(
        info = @Info(
                title = "Musicman API",
                description = "Описание всех эндпоинтов сервиса \"Musicman\"",
                contact = @Contact(name = "Анастасия Сашина", email = "sashina@cs.vsu.ru"),
                version = "1.0.5"),
        security = { @SecurityRequirement(name = "Bearer") })
public class SpringdocConfiguration {
}
