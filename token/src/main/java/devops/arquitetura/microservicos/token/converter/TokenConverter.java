package devops.arquitetura.microservicos.token.converter;

import java.nio.file.AccessDeniedException;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import devops.arquitetura.microservicos.core.rest.jwt.JwtConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenConverter {

	@SneakyThrows
	public String decriptandoToken(String tokenCriptografado) {

		log.info("Decriptando token {}", tokenCriptografado);

		JWEObject jweObject = JWEObject.parse(tokenCriptografado);
		DirectDecrypter directDecrypter = new DirectDecrypter(JwtConfiguration.PRIVATE_KEY.getBytes());
		jweObject.decrypt(directDecrypter);

		log.info("Token descriptografado . . .");

		return jweObject
					.getPayload()
					.toSignedJWT()
					.serialize();
	}

	@SneakyThrows
	public void validarTokenAssinado(String tokenAssinado) {

		log.info("Validando assinatura do token . . .");

		SignedJWT signedJWT = SignedJWT.parse(tokenAssinado);

		log.info("Token analisado ! Recuperando chave pública do token assinado . . .");

		RSAKey chavePublica = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());

		if (!signedJWT.verify(new RSASSAVerifier(chavePublica)))
			throw new AccessDeniedException("Assinatura do token inválida");

		log.info("Token com assinatura válida");
	}
}