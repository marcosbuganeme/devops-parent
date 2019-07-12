package devops.arquitetura.microservicos.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
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

	@JsonIgnore
	@ToString.Exclude
	@Column(nullable = false)
	@NotBlank(message = "Campo 'senha' deve ser preenchido")
	private String password;

	private String role;

	public ApplicationUser(@NotNull ApplicationUser applicationUser) {
		id = applicationUser.id;
		username = applicationUser.username;
		password = applicationUser.password;
		role = applicationUser.role;
	}
}