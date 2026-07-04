package br.com.borges.erp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orcamento_itens")
public class OrcamentoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento de volta para o Orçamento "Pai"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    @JsonBackReference
    private Orcamento orcamento;

    // LIMITE AUMENTADO PARA 30: Para suportar a palavra "SERVICO_MATERIAL" sem quebrar o banco
    @Column(name = "tipo_item", nullable = false, length = 30)
    private String tipoItem; 

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "servico_id")
    private Long servicoId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidade = BigDecimal.ZERO;

    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario = BigDecimal.ZERO;

    @Column(name = "valor_total_item", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotalItem = BigDecimal.ZERO;

    // Nova coluna de unidade de medida
    @Column(name = "unidade_medida", length = 10)
    private String unidadeMedida;

    // Coluna para armazenar o valor editado sem alterar o valor original do banco
    @Column(name = "valor_unitario_editado", precision = 10, scale = 2)
    private BigDecimal valorUnitarioEditado; 

    // Construtor vazio padrão do JPA
    public OrcamentoItem() {
    }

    // --- GETTERS E SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Orcamento getOrcamento() { return orcamento; }
    public void setOrcamento(Orcamento orcamento) { this.orcamento = orcamento; }

    public String getTipoItem() { return tipoItem; }
    public void setTipoItem(String tipoItem) { this.tipoItem = tipoItem; }

    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }

    public Long getServicoId() { return servicoId; }
    public void setServicoId(Long servicoId) { this.servicoId = servicoId; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

    public BigDecimal getValorTotalItem() { return valorTotalItem; }
    public void setValorTotalItem(BigDecimal valorTotalItem) { this.valorTotalItem = valorTotalItem; }

    // Getters e Setters novos incluídos
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public BigDecimal getValorUnitarioEditado() { return valorUnitarioEditado; }
    public void setValorUnitarioEditado(BigDecimal valorUnitarioEditado) { this.valorUnitarioEditado = valorUnitarioEditado; }

    // Regra de negócio organizada junto com os Getters
    public BigDecimal getValorFinalUnitario() {
        return (valorUnitarioEditado != null) ? valorUnitarioEditado : this.valorUnitario;
    }
}