package devops.arquitetura.microservices.auth.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import devops.arquitetura.microservicos.core.config.ConstanteConfig;

@Configuration
@EntityScan(basePackages = { ConstanteConfig.MODEL})
@EnableJpaRepositories(basePackages = { ConstanteConfig.REPOSITORY })
public class JpaConfig {}