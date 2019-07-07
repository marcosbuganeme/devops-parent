package devops.arquitetura.microservicos.cliente.config;

import org.springframework.context.annotation.Configuration;

import devops.arquitetura.microservicos.core.config.SwaggerBaseConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends SwaggerBaseConfig {
}