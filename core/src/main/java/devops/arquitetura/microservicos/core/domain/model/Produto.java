package devops.arquitetura.microservicos.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import devops.arquitetura.microservicos.core.domain.model.embedded.Historico;
import devops.arquitetura.microservicos.core.domain.model.embedded.ItemPedidoPK;
import devops.arquitetura.microservicos.core.domain.model.shared.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Produto implements Domain<Long> {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
	@EqualsAndHashCode.Include
	@NotNull(message = "Campo 'código' é obrigatório!")
	private Integer codigo;

	@Column(nullable = false)
	@EqualsAndHashCode.Include
	@NotNull(message = "Campo 'ncm' é obrigatório!")	
	private Integer ncm;

    @Column(nullable =  false)
    @NotBlank(message = "Campo 'nome' é obrigatório!")
    private String nome;

    @Embedded
    private Historico historico;

    @Column(name = "preco_unitario", nullable = false)
    @NotNull(message = "Campo 'preço unitário' é obrigatório!")
    private BigDecimal precoUnitario;

    @JsonIgnore
    @OneToMany(mappedBy = "id.produto")
    private List<ItemPedido> itens;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "id_produto"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private List<Categoria> categorias;

    {
    	itens = new ArrayList<>();
    	categorias = new ArrayList<>();
    }

	@PrePersist
	private void triggerInsert() {
		historico.setCadastrado(LocalDateTime.now());
	}

	@PreUpdate
	private void triggerUpdate() {
		historico.setModificado(LocalDateTime.now());
	}

    @Transient
    @JsonIgnore
    public List<Pedido> todosItensVinculados() {

    	return itens
				.stream()
				.map(ItemPedido::getId)
				.map(ItemPedidoPK::getPedido)
				.collect(Collectors.toList());
    }
}