package devops.arquitetura.microservicos.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devops.arquitetura.microservicos.core.domain.model.ItemPedido;
import devops.arquitetura.microservicos.core.domain.model.embedded.ItemPedidoPK;

public @Repository interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {}