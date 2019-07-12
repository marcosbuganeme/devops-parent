package devops.arquitetura.microservicos.cliente.controller;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import devops.arquitetura.microservicos.cliente.service.ClienteService;
import devops.arquitetura.microservicos.core.domain.model.Cliente;
import devops.arquitetura.microservicos.core.rest.controller.AbstractController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteController extends AbstractController<Cliente> {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> criarCliente(@Valid @RequestBody Cliente cliente, UriComponentsBuilder uriBuilder) {

        Cliente cadastrado = clienteService.criar(cliente);

        URI uri = uriBuilder
                    .path("clientes/{id:\\d+}")
                    .buildAndExpand(cadastrado.getId())
                    .toUri();

        return ResponseEntity
                    .created(uri)
                    .body(cadastrado);
    }

    @PutMapping("{id:\\d+}")
    public ResponseEntity<?> alterarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {

        clienteService.alterar(id, cliente);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("{id:\\d+}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {

        Cliente cliente = clienteService
                                .buscarPorId(id)
                                .orElse(null);

        return Optional
                .ofNullable(cliente)
                .filter(Objects::isNull)
                .map(recursoNaoEncontrado())
                .orElse(ResponseEntity.ok(cliente));
    }

    @GetMapping
    public ResponseEntity<?> buscarClientesPaginados(@PageableDefault Pageable pageable) {
        Page<Cliente> resultados = clienteService.buscarTodos(pageable);

        return Optional
                .ofNullable(resultados)
                .filter(porResultadosVazios())
                .map(recursosNaoEncontrado())
                .orElse(ResponseEntity.ok(resultados));
    }
}