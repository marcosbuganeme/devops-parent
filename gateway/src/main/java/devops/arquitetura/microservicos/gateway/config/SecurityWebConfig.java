package devops.arquitetura.microservicos.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import devops.arquitetura.microservicos.gateway.filter.GatewayJwtTokenAuthorizationFilter;
import devops.arquitetura.microservicos.token.config.SecurityTokenConfig;
import devops.arquitetura.microservicos.token.converter.TokenConverter;

@EnableWebSecurity
public class SecurityWebConfig extends SecurityTokenConfig {

	private final TokenConverter tokenConverter;

	public SecurityWebConfig(TokenConverter tokenConverter) {
		this.tokenConverter = tokenConverter;
	}

	protected @Override void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(new GatewayJwtTokenAuthorizationFilter(tokenConverter), UsernamePasswordAuthenticationFilter.class);
		super.configure(http);
	}
}