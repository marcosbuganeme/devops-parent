package devops.arquitetura.microservicos.cliente.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import devops.arquitetura.microservicos.core.domain.exceptions.RecursoJaExistenteException;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailDuplicadoException extends RecursoJaExistenteException {

    public EmailDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
