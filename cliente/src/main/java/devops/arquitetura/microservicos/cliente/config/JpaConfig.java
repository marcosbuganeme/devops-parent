package devops.arquitetura.microservicos.cliente.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import devops.arquitetura.microservicos.core.config.ConstanteConfig;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = { ConstanteConfig.MODEL})
@EnableJpaRepositories(basePackages = { ConstanteConfig.REPOSITORY })
public class JpaConfig {}