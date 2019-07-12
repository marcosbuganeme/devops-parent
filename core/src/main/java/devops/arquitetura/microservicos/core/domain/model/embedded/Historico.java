package devops.arquitetura.microservicos.core.domain.model.embedded;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class Historico implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy hh:MM:ss")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:MM:ss")
	private LocalDateTime cadastrado;

	@JsonFormat(pattern = "dd/MM/yyyy hh:MM:ss")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:MM:ss")
	private LocalDateTime modificado;
}