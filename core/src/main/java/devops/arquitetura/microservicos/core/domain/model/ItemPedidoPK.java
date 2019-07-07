package devops.arquitetura.microservicos.core.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class ItemPedidoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "id_pedido", nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;
}