package devops.arquitetura.microservicos.core.domain.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class ItemPedido implements Domain<ItemPedidoPK> {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	@EqualsAndHashCode.Include
	private ItemPedidoPK id;

	private BigDecimal preco;
	private Integer quantidade;
	private BigDecimal desconto;
}