package devops.arquitetura.microservicos.core.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;

public @Repository interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

	ApplicationUser findByUsername(String username);
}