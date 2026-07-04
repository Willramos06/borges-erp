package br.com.borges.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orcamentos")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer sequencial;

    @Column(nullable = false)
    private Integer ano;

    @Column(name = "codigo_visual", unique = true, nullable = false, length = 20)
    private String codigoVisual;

    // Relacionamento com o Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @Column(length = 30)
    private String status = "Pendente";

    @Column(name = "descricao_resumida", columnDefinition = "TEXT")
    private String descricaoResumida;

    @Column(name = "bdi_percentual", precision = 5, scale = 2)
    private BigDecimal bdiPercentual = BigDecimal.ZERO;

    @Column(name = "valor_subtotal", precision = 12, scale = 2)
    private BigDecimal valorSubtotal = BigDecimal.ZERO;

    @Column(name = "valor_total", precision = 12, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    // Relacionamento com os Itens do Orçamento
    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrcamentoItem> itens = new ArrayList<>();

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtor vazio padrão do JPA
    public Orcamento() {
    }

    // --- MÉTODOS AUTOMÁTICOS (Ciclo de Vida) ---
    
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // --- GETTERS E SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSequencial() { return sequencial; }
    public void setSequencial(Integer sequencial) { this.sequencial = sequencial; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getCodigoVisual() { return codigoVisual; }
    public void setCodigoVisual(String codigoVisual) { this.codigoVisual = codigoVisual; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescricaoResumida() { return descricaoResumida; }
    public void setDescricaoResumida(String descricaoResumida) { this.descricaoResumida = descricaoResumida; }

    public BigDecimal getBdiPercentual() { return bdiPercentual; }
    public void setBdiPercentual(BigDecimal bdiPercentual) { this.bdiPercentual = bdiPercentual; }

    public BigDecimal getValorSubtotal() { return valorSubtotal; }
    public void setValorSubtotal(BigDecimal valorSubtotal) { this.valorSubtotal = valorSubtotal; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public List<OrcamentoItem> getItens() { return itens; }
    public void setItens(List<OrcamentoItem> itens) { this.itens = itens; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}