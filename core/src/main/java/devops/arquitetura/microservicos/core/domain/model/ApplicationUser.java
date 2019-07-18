package devops.arquitetura.microservicos.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationUser implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Campo 'usuário' deve ser preenchido")
	private String username;

	@ToString.Exclude
	@Column(nullable = false)
	@NotBlank(message = "Campo 'senha' deve ser preenchido")
	private String password;

	@Builder.Default
    @Column(nullable = false)
	@NotNull(message = "Campo 'regra' deve ser preenchido")
	private String role = "USER";

	public ApplicationUser(@NotNull ApplicationUser applicationUser) {
		id = applicationUser.getId();
		username = applicationUser.getUsername();
		password = applicationUser.getPassword();
		role = applicationUser.getRole();
	}
}