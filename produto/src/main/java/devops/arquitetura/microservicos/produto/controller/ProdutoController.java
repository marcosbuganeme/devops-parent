package devops.arquitetura.microservicos.produto.controller;

import java.net.URI;
import java.util.List;
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

import devops.arquitetura.microservicos.core.domain.model.Produto;
import devops.arquitetura.microservicos.core.rest.controller.AbstractController;
import devops.arquitetura.microservicos.produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoController extends AbstractController<Produto> {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> criarProduto(@Valid @RequestBody Produto produto, UriComponentsBuilder uriBuilder) {

        Produto cadastrado = produtoService.criar(produto);

        URI uri = uriBuilder
                    .path("produtos/{id:\\d+}")
                    .buildAndExpand(cadastrado.getId())
                    .toUri();

        return ResponseEntity
                .created(uri)
                .body(cadastrado);
    }

    @PutMapping("{id:\\d+}")
    public ResponseEntity<?> alterarProduto(@PathVariable Long id, @Valid @RequestBody Produto produto) {

        produtoService.alterar(id, produto);

        return ResponseEntity.ok(produto);
    }

    @GetMapping("{id:\\d+}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {

        Produto produto = produtoService
                            .buscarPorId(id)
                            .orElse(null);

        return Optional
                .ofNullable(produto)
                .filter(Objects::isNull)
                .map(recursoNaoEncontrado())
                .orElse(ResponseEntity.ok(produto));
    }
    
    @GetMapping()
    public ResponseEntity<?> buscarPorNomeDoProduto(@PathVariable String produto) {

        List<Produto> resultados = produtoService.buscarPorAutoComplete(produto);

        return Optional
                .ofNullable(resultados)
                .filter(results -> results.isEmpty())
                .map(result -> ResponseEntity.notFound().build())
                .orElse(ResponseEntity.ok(resultados));
    }

    @GetMapping
    public ResponseEntity<?> buscarProdutosPaginados(@PageableDefault Pageable pageable) {

        Page<Produto> resultados = produtoService.buscarTodos(pageable);

        return Optional
                .ofNullable(resultados)
                .filter(porResultadosVazios())
                .map(recursosNaoEncontrado())
                .orElse(ResponseEntity.ok(resultados));
    }
}
