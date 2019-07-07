package devops.arquitetura.microservicos.cliente.service;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import devops.arquitetura.microservicos.core.domain.exceptions.RecursoJaExistenteException;
import devops.arquitetura.microservicos.core.domain.exceptions.RecursoNaoEncontradoException;
import devops.arquitetura.microservicos.core.domain.model.Cliente;
import devops.arquitetura.microservicos.core.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente criar(Cliente cliente) {

        return verificaRegraDeEmailDo(cliente)
                    .e()
                .aplicaLog(cliente)
                    .e()
                .gravaNoBancoDeDados(cliente);
    }

    @Transactional
    public Cliente alterar(Long id, Cliente cliente) {

        buscarPorId(id)
        		.map(Cliente::getEmail)
        		.filter(StringUtils::isEmpty)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não existe"));

        cliente.setId(id);

        return gravaNoBancoDeDados(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {

        log.info("cliente {} foi pesquisado", id);

        return clienteRepository.findById(id);
    }

    public Page<Cliente> buscarTodos(Pageable pageable) {

        log.info("todos clientes foram pesquisados de forma paginada");

        return clienteRepository.findAll(pageable);
    }

    private ClienteService verificaRegraDeEmailDo(Cliente cliente) {

        Cliente clientePesquisado = clienteRepository.findByEmail(cliente.getEmail());

        if (Objects.nonNull(clientePesquisado))
        	throw new RecursoJaExistenteException("E-mail já cadastrado");	

        return this;
    }

    private ClienteService aplicaLog(Cliente cliente) {

        log.info("Cliente {} foi cadastrado", cliente);

        return this;
    }

    private Cliente gravaNoBancoDeDados(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    private ClienteService e() { return this; }
}