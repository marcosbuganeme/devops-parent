package devops.arquitetura.microservicos.token.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;
import devops.arquitetura.microservicos.token.converter.TokenConverter;
import devops.arquitetura.microservicos.token.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

	protected final TokenConverter tokenConverter;

	protected @Override void doFilterInternal(@NotNull HttpServletRequest request, 
											  @NotNull HttpServletResponse response, 
											  FilterChain filterChain) throws ServletException, IOException {
		
		String header = request.getHeader(JwtConfiguration.HEADER_NAME);
		if (header == null || !header.startsWith(JwtConfiguration.HEADER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = removeBearer(header);
		SecurityContextUtil.setSecurityContext(verificaRetornaTipoToken(token));
		filterChain.doFilter(request, response);
	}

	protected String removeBearer(String header) {
		return header.replace(JwtConfiguration.HEADER_PREFIX, "").trim();
	}

	private SignedJWT verificaRetornaTipoToken(String token) {
		return StringUtils.equalsIgnoreCase("signed", JwtConfiguration.TYPE) ? validando(token): validandoDescriptografia(token);
	}

	@SneakyThrows
	private SignedJWT validandoDescriptografia(String tokenCriptografado) {

		String tokenAssinado = tokenConverter.decriptandoToken(tokenCriptografado);
		tokenConverter.validarTokenAssinado(tokenAssinado);

		return SignedJWT.parse(tokenAssinado);
	}

	@SneakyThrows
	private SignedJWT validando(String tokenAssinado) {

		tokenConverter.validarTokenAssinado(tokenAssinado);
		return SignedJWT.parse(tokenAssinado);
	}
}