package devops.arquitetura.microservicos.core.domain.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devops.arquitetura.microservicos.core.domain.model.Produto;

public @Repository interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByNome(String nome);

    Stream<Produto> readAllByNomeContainingIgnoreCaseOrderByNome(String nome);
}