package devops.arquitetura.microservicos.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devops.arquitetura.microservicos.core.domain.model.Cliente;

public @Repository interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByEmail(String email);
}