package devops.arquitetura.microservicos.core.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import devops.arquitetura.microservicos.core.domain.model.embedded.Historico;
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
@Table(name = "endereco")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Endereco implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cep;
	private String numero;
	private String bairro;
	private String logradouro;
	private String complemento;

	@Embedded
	private Historico historico;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_cidade", nullable = false)
	private Cidade cidade;

	@PrePersist
	private void triggerInsert() {
		historico.setCadastrado(LocalDateTime.now());
	}

	@PreUpdate
	private void triggerUpdate() {
		historico.setModificado(LocalDateTime.now());
	}
}