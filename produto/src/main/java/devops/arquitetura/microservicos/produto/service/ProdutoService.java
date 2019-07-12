package devops.arquitetura.microservicos.produto.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devops.arquitetura.microservicos.core.domain.exceptions.RecursoJaExistenteException;
import devops.arquitetura.microservicos.core.domain.exceptions.RecursoNaoEncontradoException;
import devops.arquitetura.microservicos.core.domain.model.Produto;
import devops.arquitetura.microservicos.core.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto criar(Produto produto) {

        return verificaRegraPorNome(produto)
                     .e()
                .aplicaLog("cadastrado", produto)
                     .e()
                .gravaNoBancoDeDados(produto);
    }

    @Transactional
    public Produto alterar(Long id, Produto produto) {

    	buscarPorId(id)
	    	.orElseThrow(() -> new RecursoNaoEncontradoException("Produto não existe"));

        produto.setId(id);

        return aplicaLog("alterado", produto)
        			.e()
        		.gravaNoBancoDeDados(produto);
    }

    @Transactional
    public void remover(Long id) {

        buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não existe"));

        produtoRepository.deleteById(id);
    }

    public Optional<Produto> buscarPorId(Long id) {

        log.info("Produto {} foi pesquisado", id);

        return produtoRepository.findById(id);
    }

    public Page<Produto> buscarTodos(Pageable pageable) {

        log.info("Todos produtos foram pesquisados de forma paginada");

        return produtoRepository.findAll(pageable);
    }

    public List<Produto> buscarPorAutoComplete(String produto) {

    	return produtoRepository
		    		.readAllByNomeContainingIgnoreCaseOrderByNome(produto)
		    		.collect(Collectors.toList());
    }

    private ProdutoService verificaRegraPorNome(Produto produto) {

        Produto produtoPesquisado = produtoRepository.findByNome(produto.getNome());

        Optional
            .ofNullable(produtoPesquisado)
            .filter(Objects::nonNull)
            .orElseThrow(() -> new RecursoJaExistenteException("Produto já foi cadastrado !"));

        return this;
    }

    private ProdutoService aplicaLog(String acao, Produto produto) {

        log.info("Produto {} foi {}", produto, acao);

        return this;
    }

    private Produto gravaNoBancoDeDados(Produto produto) {

        return produtoRepository.save(produto);
    }

    private ProdutoService e() { return this; }
}
