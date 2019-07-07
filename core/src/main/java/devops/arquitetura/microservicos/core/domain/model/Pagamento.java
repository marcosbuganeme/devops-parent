package devops.arquitetura.microservicos.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import devops.arquitetura.microservicos.core.domain.model.enuns.SituacaoPagamento;
import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonTypeInfo(property = "@type", use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
public abstract class Pagamento implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SituacaoPagamento situacao;

	@MapsId
	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "id_pedido", nullable = false)
	private Pedido pedido;
}
