package devops.arquitetura.microservicos.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devops.arquitetura.microservicos.core.domain.model.Pedido;

public @Repository interface PedidoRepository extends JpaRepository<Pedido, Long> {}