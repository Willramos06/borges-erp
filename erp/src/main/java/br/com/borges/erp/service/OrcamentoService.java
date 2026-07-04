package br.com.borges.erp.service;

import br.com.borges.erp.model.Material;
import br.com.borges.erp.model.Orcamento;
import br.com.borges.erp.model.OrcamentoItem;
import br.com.borges.erp.repository.MaterialRepository;
import br.com.borges.erp.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    // CORREÇÃO: Método listarTodos adicionado para fazer a paginação funcionar
    public Page<Orcamento> listarTodos(Pageable paginacao) {
        return orcamentoRepository.findAll(paginacao);
    }

    @Transactional
    public Orcamento salvar(Orcamento orcamento) {
        int anoAtual = LocalDate.now().getYear();
        Integer ultimoSequencial = orcamentoRepository.findMaxSequencialByAno(anoAtual);
        int proximoSequencial = (ultimoSequencial == null) ? 1 : ultimoSequencial + 1;
        
        orcamento.setAno(anoAtual);
        orcamento.setSequencial(proximoSequencial);
        orcamento.setCodigoVisual(String.format("%03d/%d", proximoSequencial, anoAtual));
        
        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public Orcamento atualizarStatus(Long id, String novoStatus) {
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));

        String statusAntigo = orcamento.getStatus();
        orcamento.setStatus(novoStatus);

        if (!"Concluído".equalsIgnoreCase(statusAntigo) && "Concluído".equalsIgnoreCase(novoStatus)) {
            
            if (orcamento.getItens() != null) {
                for (OrcamentoItem item : orcamento.getItens()) {
                    if ("MATERIAL".equalsIgnoreCase(item.getTipoItem()) && item.getMaterialId() != null) {
                        
                        Material material = materialRepository.findById(item.getMaterialId())
                                .orElseThrow(() -> new RuntimeException("Material não encontrado no banco"));
                        
                        BigDecimal estoqueAtual = material.getEstoqueAtual() != null ? material.getEstoqueAtual() : BigDecimal.ZERO;
                        BigDecimal quantidadeGasta = item.getQuantidade() != null ? new BigDecimal(item.getQuantidade().toString()) : BigDecimal.ZERO;
                        
                        material.setEstoqueAtual(estoqueAtual.subtract(quantidadeGasta));
                        materialRepository.save(material);
                    }
                }
            }
        }

        return orcamentoRepository.save(orcamento);
    }
}