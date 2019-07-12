package devops.arquitetura.microservicos.core.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devops.arquitetura.microservicos.core.domain.model.embedded.ItemPedidoPK;
import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class ItemPedido implements Domain<ItemPedidoPK> {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	@EqualsAndHashCode.Include
	private ItemPedidoPK id;

	private BigDecimal valor;

	@Column(nullable = false)
	@NotNull(message = "Campo 'quantidade' é obrigatório!")
	private Integer quantidade;

	private BigDecimal desconto;

	{
		desconto = BigDecimal.ZERO;
	}

	@PrePersist
	private void triggerInsert() {
		valor = calculaPrecoPorItemAdicionado();
	}

	private BigDecimal calculaPrecoPorItemAdicionado() {
		return precoUnitarioDoProduto().multiply(quantidade()).subtract(desconto);
	}
	
	private @Transient BigDecimal precoUnitarioDoProduto() {
		return id.getProduto().getPrecoUnitario();
	}

	private @Transient BigDecimal quantidade() {
		return new BigDecimal(quantidade);
	}
}