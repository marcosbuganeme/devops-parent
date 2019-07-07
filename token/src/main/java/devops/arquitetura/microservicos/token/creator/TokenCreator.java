package devops.arquitetura.microservicos.token.creator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;
import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenCreator {

	@SneakyThrows
	public SignedJWT assinarToken(Authentication auth) {
		log.info("iniciando a assinatura do token");

		ApplicationUser user = (ApplicationUser) auth.getPrincipal();
		JWTClaimsSet jwtClaimSet = criarJwtClaimSet(auth, user);
		KeyPair parDeChaves = gerarParDeChaves();

		log.info("Construindo JWK através do par de chaves RSA");

		JWK jwk = new RSAKey
						.Builder((RSAPublicKey) parDeChaves.getPublic())
						.keyID(UUID.randomUUID().toString())
						.build();

		SignedJWT tokenAssinado = new SignedJWT(new JWSHeader
														.Builder(JWSAlgorithm.RS256)
														.jwk(jwk)
														.type(JOSEObjectType.JWT)
														.build(), jwtClaimSet);

		RSASSASigner assinatura = new RSASSASigner(parDeChaves.getPrivate());
		tokenAssinado.sign(assinatura);

		log.info("Token assinado '{}'", tokenAssinado.serialize());

		return tokenAssinado;
	}

	public String criptografarToken(SignedJWT tokenAssinado) throws JOSEException {

		log.info("Começando a criptografia do token . . .");

		DirectEncrypter encriptador = new DirectEncrypter(JwtConfiguration.PRIVATE_KEY.getBytes());

		JWEObject jweObject = new JWEObject(new JWEHeader
												.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
												.contentType("JWT")
												.build(), new Payload(tokenAssinado));

		log.info("Criptografando token com chave privada assimetrica");

		jweObject.encrypt(encriptador);

		log.info("Token criptografado");

		return jweObject.serialize();
	}

	private JWTClaimsSet criarJwtClaimSet(Authentication auth, ApplicationUser user) {
		log.info("Criando claim set");

		return new JWTClaimsSet
						.Builder()
						.subject(user.getUsername())
						.claim("authorities", 
								auth
								.getAuthorities()
								.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
						.claim("userId", user.getId())
						.issuer("https://github.com/marcosbuganeme/arquitetura-microservicos")
						.issueTime(new Date())
						.expirationTime(umaHora())
						.build();
	}

	@SneakyThrows
	private KeyPair gerarParDeChaves() {
		log.info("Gerando par de chaves RSA 2048 bits");

		KeyPairGenerator chaves = KeyPairGenerator.getInstance("RSA");
		chaves.initialize(2048);

		return chaves.generateKeyPair();
	}

	private Date umaHora() {
		return new Date(System.currentTimeMillis() + (JwtConfiguration.EXPIRATION * 1000));
	}
}