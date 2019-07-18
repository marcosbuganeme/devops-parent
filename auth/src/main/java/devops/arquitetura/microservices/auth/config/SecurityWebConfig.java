package devops.arquitetura.microservices.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import devops.arquitetura.microservices.auth.filter.JwtAuthenticationFilter;
import devops.arquitetura.microservicos.token.config.SecurityTokenConfig;
import devops.arquitetura.microservicos.token.converter.TokenConverter;
import devops.arquitetura.microservicos.token.creator.TokenCreator;
import devops.arquitetura.microservicos.token.filter.JwtTokenAuthorizationFilter;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityWebConfig extends SecurityTokenConfig {

	private final TokenCreator tokenCreator;
	private final TokenConverter tokenConverter;
	private final @Qualifier("UserDetailsServiceImpl") UserDetailsService userDetailsService;

	protected @Override void configure(HttpSecurity http) throws Exception {

		http
			.addFilter(new JwtAuthenticationFilter(tokenCreator, authenticationManager()))
			.addFilterAfter(new JwtTokenAuthorizationFilter(tokenConverter), UsernamePasswordAuthenticationFilter.class);

		super.configure(http);
	}

	protected @Override void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	public @Bean BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}