package devops.arquitetura.microservicos.core.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Campo 'nome' é obrigatório!")
	private String nome;

	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos;

	{
		produtos = new ArrayList<>();
	}
}