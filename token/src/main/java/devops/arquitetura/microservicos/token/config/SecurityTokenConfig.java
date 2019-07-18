package devops.arquitetura.microservicos.token.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;

public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	private static final String[] URLS_LIBERADAS = { 
			"/**/v2/api-docs/**", 
			"/**/swagger-ui.html",
			"/**/swagger-resources/**", 
			JwtConfiguration.LOGIN_URL,
			"/**/webjars/springfox-swagger-ui/**" 
		};

	protected @Override void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.authorizeRequests()
			.antMatchers(URLS_LIBERADAS).permitAll()
			.antMatchers("/produtos/**").hasRole("USER")
			.antMatchers("/clientes/**").hasRole("USER")
			.anyRequest().authenticated()
			.and()
		.exceptionHandling().authenticationEntryPoint((request, response, error) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
	}
}