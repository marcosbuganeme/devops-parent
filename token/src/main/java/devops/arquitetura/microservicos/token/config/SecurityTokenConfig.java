package devops.arquitetura.microservicos.token.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;

public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	protected @Override void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.exceptionHandling().authenticationEntryPoint((req, resp, error) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
		.authorizeRequests()
			.antMatchers(JwtConfiguration.LOGIN_URL, "/**/swagger-ui.html").permitAll()
            .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**").permitAll()
			.antMatchers("/produto/**").hasRole("USER")
			.antMatchers("/cliente/**").hasRole("USER")
			.anyRequest().authenticated();
	}
}