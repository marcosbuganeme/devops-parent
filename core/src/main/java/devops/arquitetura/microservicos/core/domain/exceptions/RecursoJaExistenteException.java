package devops.arquitetura.microservicos.core.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class RecursoJaExistenteException extends RuntimeException {

    public RecursoJaExistenteException(String mensagem) {
        super(mensagem);
    }
}