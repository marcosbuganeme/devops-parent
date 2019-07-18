package devops.arquitetura.microservices.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import devops.arquitetura.microservices.auth.model.UsuarioAutenticado;
import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;
import devops.arquitetura.microservicos.core.domain.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("UserDetailsServiceImpl")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    public @Override UserDetails loadUserByUsername(String username) {
		log.info("Pesquisando usuário no banco de dados '{}'", username);

        ApplicationUser usuario = applicationUserRepository.findByUsername(username);

        log.info("Usuário encontrado '{}'", usuario);

        Optional
        	.ofNullable(usuario)
        	.orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário '%s' não encontrado", username)));

        return new UsuarioAutenticado(usuario);
    }
}