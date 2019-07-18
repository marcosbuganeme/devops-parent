package devops.arquitetura.microservices.auth.model;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import devops.arquitetura.microservicos.core.domain.model.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class UsuarioAutenticado implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ApplicationUser user;

	public @Override Collection<? extends GrantedAuthority> getAuthorities() {
		return commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return user.getPassword();
	}
}