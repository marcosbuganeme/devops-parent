package devops.arquitetura.microservicos.core.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devops.arquitetura.microservicos.core.domain.model.embedded.Historico;
import devops.arquitetura.microservicos.core.domain.model.enuns.Perfil;
import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Cliente implements Domain<Long> {

	private static final int ZERADA = 0;
	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Campo 'nome' é obrigatório!")
	private String nome;

	@Email(message = "E-mail inválido!")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Campo 'email' é obrigatório!")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "Campo 'documento' é obrigatório!")
	private String documento;

	private Integer pontuacao;

	@Embedded
	private Historico historico;

	@OneToOne
	@JoinColumn(name = "id_usuario")
	private ApplicationUser usuario;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "telefone", joinColumns = @JoinColumn(name = "id_telefone"))
	private List<String> telefones;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;

	{
		telefones = new ArrayList<>();
		enderecos = new ArrayList<>();
		pedidos = new ArrayList<>();
	}

	@PrePersist
	private void triggerInsert() {
		pontuacao = ZERADA;
		historico.setCadastrado(LocalDateTime.now());
		usuario.setRole(Perfil.CLIENTE.getDescricao());
	}

	@PreUpdate
	private void triggerUpdate() {
		historico.setModificado(LocalDateTime.now());
	}
}