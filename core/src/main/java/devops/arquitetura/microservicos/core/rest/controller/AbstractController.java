package devops.arquitetura.microservicos.core.rest.controller;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;

public abstract class AbstractController<T extends Domain<?>> {

    protected Function<T, ResponseEntity<T>> recursoNaoEncontrado() {
        return object -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(object);
    }

    protected Function<Page<T>, ResponseEntity<Page<T>>> recursosNaoEncontrado() {
        return page -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(page);
    }
}