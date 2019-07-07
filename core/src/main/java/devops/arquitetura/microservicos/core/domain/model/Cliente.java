package devops.arquitetura.microservicos.core.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String documento;

	private Integer perfil;

	@JsonIgnore
	private String senha;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos;

	@CollectionTable(name = "PERFIL")
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Integer> perfis;

	@ElementCollection
	@CollectionTable(name = "TELEFONE", joinColumns = @JoinColumn(name = "id_telefone"))
	private List<String> telefones;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;

	{
		enderecos = new ArrayList<>();
		perfis = new ArrayList<>();
		telefones = new ArrayList<>();
		pedidos = new ArrayList<>();
	}

	@PrePersist
	private void adicionarPerfil() {
		perfis.add(Perfil.CLIENTE.ordinal());
	}
}