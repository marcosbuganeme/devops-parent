package devops.arquitetura.microservicos.token.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityContextUtil {

	private SecurityContextUtil() {}

	public static void setSecurityContext(SignedJWT signedJwt) {

		try {

			JWTClaimsSet claims = signedJwt.getJWTClaimsSet();
			String username = claims.getSubject();

			Optional
				.ofNullable(username)
				.orElseThrow(() -> new JOSEException("Usuário do token não encontrado"));

			List<String> authorities = claims.getStringListClaim("authorities");
			ApplicationUser user = ApplicationUser
										.builder()
										.id(claims.getLongClaim("userId"))
										.username(username)
										.role(String.join(",", authorities))
										.build();

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, transformar(authorities));
			authToken.setDetails(signedJwt.serialize());
			SecurityContextHolder.getContext().setAuthentication(authToken);

		} catch (Exception exception) {

			log.error("Erro ao inserir contexto de segurança. Exceção: {}", exception);
			SecurityContextHolder.clearContext();
		}
	}

	private static List<SimpleGrantedAuthority> transformar(List<String> authorities) {

		return authorities
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
}