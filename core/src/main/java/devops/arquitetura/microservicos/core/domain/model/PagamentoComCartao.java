package devops.arquitetura.microservicos.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@JsonTypeName("pagamentoComCartao")
@EqualsAndHashCode(callSuper = true)
public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = 1L;

	@Column(name = "numero_de_parcelas")
	private Integer numeroDeParcelas;
}