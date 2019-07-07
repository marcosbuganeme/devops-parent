package devops.arquitetura.microservicos.gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;

@Configuration
@EnableConfigurationProperties(value = JwtConfiguration.class)
public class JwtConfig {}