package br.com.borges.erp.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResposta {

    private LocalDateTime timestamp;
    private Integer status;
    private String titulo;
    private String mensagem;
    private List<String> detalhes; // Usado para listar campos inválidos (ex: CPF incorreto, Email vazio)

    public ErroResposta(Integer status, String titulo, String mensagem, List<String> detalhes) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

    // GETTERS
    public LocalDateTime getTimestamp() { return timestamp; }
    public Integer getStatus() { return status; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public List<String> getDetalhes() { return detalhes; }
}