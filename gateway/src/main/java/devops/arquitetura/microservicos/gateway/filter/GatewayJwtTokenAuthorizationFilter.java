package devops.arquitetura.microservicos.gateway.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.context.RequestContext;

import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;
import devops.arquitetura.microservicos.token.converter.TokenConverter;
import devops.arquitetura.microservicos.token.filter.JwtTokenAuthorizationFilter;
import lombok.SneakyThrows;

public class GatewayJwtTokenAuthorizationFilter extends JwtTokenAuthorizationFilter {

	@Autowired
	public GatewayJwtTokenAuthorizationFilter(TokenConverter tokenConverter) {
		super(tokenConverter);
	}

	@Override
	@SneakyThrows
	protected void doFilterInternal(@NotNull HttpServletRequest request,
								    @NotNull HttpServletResponse response, 
								    FilterChain filterChain) throws ServletException, IOException {

		String header = request.getHeader(JwtConfiguration.HEADER_NAME);
		if (header == null || !header.startsWith(JwtConfiguration.HEADER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = removeBearer(header);
		String tokenAssinado = tokenConverter.decriptandoToken(token);
		tokenConverter.validarTokenAssinado(tokenAssinado);

		if (JwtConfiguration.TYPE.equals("signed"))
			RequestContext.getCurrentContext().addZuulRequestHeader("Authorization", JwtConfiguration.HEADER_PREFIX + tokenAssinado);

		filterChain.doFilter(request, response);
	}
}