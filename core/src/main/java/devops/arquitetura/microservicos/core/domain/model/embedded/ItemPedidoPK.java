package devops.arquitetura.microservicos.core.domain.model.embedded;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import devops.arquitetura.microservicos.core.domain.model.Pedido;
import devops.arquitetura.microservicos.core.domain.model.Produto;
import lombok.Data;

@Data
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