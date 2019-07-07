package devops.arquitetura.microservicos.core.domain.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@JsonTypeName("pagamentoComCartao")
@EqualsAndHashCode(callSuper = true)
public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;
}