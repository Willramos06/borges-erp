package br.com.borges.erp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "materiais")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "valor_custo", precision = 10, scale = 2)
    private BigDecimal valorCusto = BigDecimal.ZERO;

    @Column(name = "valor_venda", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @Column(name = "estoque_atual", precision = 10, scale = 2)
    private BigDecimal estoqueAtual = BigDecimal.ZERO;

    // Construtor vazio padrão
    public Material() {
    }

    // --- GETTERS E SETTERS ---

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getDescricao() { 
        return descricao; 
    }
    
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }

    public BigDecimal getValorCusto() { 
        return valorCusto; 
    }
    
    public void setValorCusto(BigDecimal valorCusto) { 
        this.valorCusto = valorCusto; 
    }

    public BigDecimal getValorVenda() { 
        return valorVenda; 
    }
    
    public void setValorVenda(BigDecimal valorVenda) { 
        this.valorVenda = valorVenda; 
    }

    public BigDecimal getEstoqueAtual() { 
        return estoqueAtual; 
    }
    
    public void setEstoqueAtual(BigDecimal estoqueAtual) { 
        this.estoqueAtual = estoqueAtual; 
    }
}