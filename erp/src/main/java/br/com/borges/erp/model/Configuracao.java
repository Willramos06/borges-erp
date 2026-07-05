package br.com.borges.erp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracoes")
public class Configuracao {

    @Id
    private Long id = 1L; 

    @NotBlank(message = "A Razão Social é obrigatória")
    private String nomeEmpresa;

    @NotBlank(message = "O CNPJ é obrigatório")
    @CNPJ(message = "O CNPJ informado é inválido")
    private String cnpj;

    @Email(message = "O formato do e-mail é inválido")
    private String email;

    @NotBlank(message = "O número de celular é obrigatório")
    private String celular;

    @Column(name = "logo_base64", columnDefinition = "TEXT")
    private String logoBase64;

    // ... restante do código segue igual
    private String cep;
    private String endereco;
    private String cidade;
    private String estado;
    private BigDecimal bdiPadrao;

    public Configuracao() {}

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getBdiPadrao() { return bdiPadrao; }
    public void setBdiPadrao(BigDecimal bdiPadrao) { this.bdiPadrao = bdiPadrao; }


    // ... manter os getters e setters anteriores ...

    public String getLogoBase64() { return logoBase64; }
    public void setLogoBase64(String logoBase64) { this.logoBase64 = logoBase64; }
}