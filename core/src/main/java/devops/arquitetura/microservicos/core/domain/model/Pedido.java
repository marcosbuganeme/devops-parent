package devops.arquitetura.microservicos.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Pedido implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime instante;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
	private Pagamento pagamento;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_endereco_entrega", nullable = false)
	private Endereco enderecoDeEntrega;

	private BigDecimal total;

	@OneToMany(mappedBy = "id.pedido")
	private List<ItemPedido> itens;

	{
		itens = new ArrayList<>();
	}

	private @PrePersist void triggerInsert() {
		fecharPedido();
		instante = LocalDateTime.now();
	}

	private void fecharPedido() {

		itens
		.stream()
		.map(ItemPedido::getValor)
		.forEach(precoPorItem -> total = total.add(precoPorItem));
	}
}