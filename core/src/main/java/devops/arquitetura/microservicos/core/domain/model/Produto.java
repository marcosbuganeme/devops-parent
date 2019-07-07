package devops.arquitetura.microservicos.core.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded =  true)
public final class Produto implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false)
    @NotBlank(message = "Campo 'nome' é obrigatório!")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "Campo 'preço' é obrigatório!")
    private BigDecimal preco;

    @JsonIgnore
    @OneToMany(mappedBy = "id.produto")
    private List<ItemPedido> itens;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "PRODUTO_CATEGORIA", joinColumns = @JoinColumn(name = "id_produto"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private List<Categoria> categorias;

    {
    	itens = new ArrayList<>();
    	categorias = new ArrayList<>();
    }

    @Transient
    @JsonIgnore
    public List<Pedido> pedidos() {

    	return itens
				.stream()
				.map(ItemPedido::getId)
				.map(ItemPedidoPK::getPedido)
				.collect(Collectors.toList());
    }
}