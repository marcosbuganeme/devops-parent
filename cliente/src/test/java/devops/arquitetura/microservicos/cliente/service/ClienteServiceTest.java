package devops.arquitetura.microservicos.cliente.service;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import devops.arquitetura.microservicos.core.domain.exceptions.RecursoJaExistenteException;
import devops.arquitetura.microservicos.core.domain.model.Cliente;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClienteServiceTest {

	private Cliente cliente;

	private @Autowired ClienteService clienteService;
	public @Rule ExpectedException expectedException = ExpectedException.none();

	public @Before void inicializar() {
		cliente = Cliente
					.builder()
					.nome("marcos olavo")
					.email("marcos.after@gmail.com")
					.documento("03287476106")
					.build();
	}

	@Test
	public void testCriarComEmailNulo() throws ConstraintViolationException {

		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("Campo 'email' é obrigatório!");

		cliente.setEmail(null);
		clienteService.criar(cliente);
	}

	@Test
	public void testCriarComEmailJaExistente() throws RecursoJaExistenteException {

		expectedException.expect(RecursoJaExistenteException.class);
		expectedException.expectMessage("E-mail já cadastrado");

		clienteService.criar(cliente);
		clienteService.criar(cliente);
	}

	@Test
	public void testCriar() throws Exception {

		clienteService.criar(cliente);
	}

	@Test
	public void testAlterar() throws Exception {

		Cliente novoCliente = clienteService.criar(cliente);
		novoCliente.setEmail("novoemail@emailteste.com");
		clienteService.alterar(novoCliente.getId(), novoCliente);
	}	
}