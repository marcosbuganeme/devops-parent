package devops.arquitetura.microservices.auth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import devops.arquitetura.microservicos.core.config.ConstanteConfig;

@Configuration
@ComponentScan(ConstanteConfig.PACKAGE_DEFAULT)
public class ComponentScanConfig {}