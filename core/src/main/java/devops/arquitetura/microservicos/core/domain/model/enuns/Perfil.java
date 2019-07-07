package devops.arquitetura.microservicos.core.domain.model.enuns;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),

	CLIENTE(2, "ROLE_CLIENTE");

	private final int codigo;
	private final String descricao;

	public static Perfil toEnum(Integer codigo) {

		return Arrays
				.stream(Perfil.values())
				.filter(apenasPerfisValidos(codigo))
				.findFirst()
				.orElseThrow(lancaExcecaoCodigoInvalido());
	}

	private static Predicate<Perfil> apenasPerfisValidos(Integer codigo) {
		return perfil -> codigo.equals(perfil.getCodigo());
	}

	private static Supplier<IllegalArgumentException> lancaExcecaoCodigoInvalido() {
		return () -> new IllegalArgumentException("Código informado é inválido");
	}
}