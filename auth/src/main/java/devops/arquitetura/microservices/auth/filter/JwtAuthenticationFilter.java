package devops.arquitetura.microservices.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;

import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;
import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;
import devops.arquitetura.microservicos.token.creator.TokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final TokenCreator tokenCreator;
	private final AuthenticationManager authManager;

	@Override
	@SneakyThrows
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);

		log.info("Autenticando '{}' . . . ", user);

		Optional
			.ofNullable(user)
			.orElseThrow(() -> new UsernameNotFoundException("Incapaz de recuperar os dados do usuário"));

		log.info("Criando objeto de autenticação para '{}'", user);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), todasPermissoes(user));
		token.setDetails(user);

		return authManager.authenticate(token);
	}

	private Collection<? extends GrantedAuthority> todasPermissoes(ApplicationUser user) {
		return Stream.of(user).map(ApplicationUser::getRole).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain, 
											Authentication authResult) throws IOException, ServletException {

		log.info("Sucesso ao autenticar usuário '{}', token JWE foi gerado", authResult.getName());

		SignedJWT tokenAssinado = tokenCreator.assinarToken(authResult);
		String tokenCriptografado = tokenCreator.criptografarToken(tokenAssinado);

		log.info("Um novo token foi gerado e incluido no cabeçalho da resposta da solicitação");

		response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + JwtConfiguration.HEADER_NAME);
		response.addHeader(JwtConfiguration.HEADER_NAME, JwtConfiguration.HEADER_PREFIX + tokenCriptografado);
	}
}