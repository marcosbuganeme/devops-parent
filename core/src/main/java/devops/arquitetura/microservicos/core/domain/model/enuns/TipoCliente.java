package devops.arquitetura.microservicos.core.domain.model.enuns;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Física"),

	PESSOA_JURIDICA(2, "Pessoa Jurídica");

	private final int codigo;
	private final String descricao;

	public static TipoCliente toEnum(Integer codigo) {

		return Arrays
				.stream(TipoCliente.values())
				.filter(apenasSituacoesValidos(codigo))
				.findFirst()
				.orElseThrow(lancaExcecaoCodigoInvalido());
	}

	private static Predicate<TipoCliente> apenasSituacoesValidos(Integer codigo) {
		return perfil -> codigo.equals(perfil.getCodigo());
	}

	private static Supplier<IllegalArgumentException> lancaExcecaoCodigoInvalido() {
		return () -> new IllegalArgumentException("Código informado é inválido");
	}
}