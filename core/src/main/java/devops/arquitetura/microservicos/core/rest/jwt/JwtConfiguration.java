package devops.arquitetura.microservicos.core.rest.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.ToString;

@ToString
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfiguration {

	public static final int EXPIRATION = 3600;
	public static final String TYPE = "encrypted";
	public static final String LOGIN_URL = "/auth/login";
	public static final String HEADER_PREFIX = "Bearer ";
	public static final String HEADER_NAME = "Authorization";
	public static final String PRIVATE_KEY = "qxBEEQv7E8aviX1KUcdOiF5ve5COUPAr";
}