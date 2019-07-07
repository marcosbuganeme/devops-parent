package devops.arquitetura.microservices.auth.service;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import java.util.Collection;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;
import devops.arquitetura.microservicos.core.domain.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@SuppressWarnings("serial")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    public @Override UserDetails loadUserByUsername(String username) {
		log.info("Pesquisando usuário no banco de dados '{}'", username);

        ApplicationUser usuarioPesquisado = applicationUserRepository.findByUsername(username);

        log.info("Usuário encontrado '{}'", usuarioPesquisado);

        Optional
        	.ofNullable(usuarioPesquisado)
        	.orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário '%s' não encontrado", username)));

        return new CustomUserDetails(usuarioPesquisado);
    }

	private static final class CustomUserDetails extends ApplicationUser implements UserDetails {

    	CustomUserDetails(@NotNull ApplicationUser applicationUser) {
            super(applicationUser);
        }

        public @Override Collection<? extends GrantedAuthority> getAuthorities() {
            return commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
        }

        public boolean isAccountNonExpired() {
            return true;
        }

        public @Override boolean isAccountNonLocked() {
            return true;
        }

        public @Override boolean isCredentialsNonExpired() {
            return true;
        }

        public @Override boolean isEnabled() {
            return true;
        }
    }
}