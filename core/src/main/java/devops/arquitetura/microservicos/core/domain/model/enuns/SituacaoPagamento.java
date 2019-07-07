package devops.arquitetura.microservicos.core.domain.model.enuns;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SituacaoPagamento {

	PENDENTE(1, "Pendente"),

	QUITADO(2, "Quitado"),

	CANCELADO(3, "Cancelado");

	private final int codigo;
	private final String descricao;

	public static SituacaoPagamento toEnum(Integer codigo) {

		return Arrays
				.stream(SituacaoPagamento.values())
				.filter(apenasSituacoesValidos(codigo))
				.findFirst()
				.orElseThrow(lancaExcecaoCodigoInvalido());
	}

	private static Predicate<SituacaoPagamento> apenasSituacoesValidos(Integer codigo) {
		return situacao -> codigo.equals(situacao.getCodigo());
	}

	private static Supplier<IllegalArgumentException> lancaExcecaoCodigoInvalido() {
		return () -> new IllegalArgumentException("Código informado é inválido");
	}
}
