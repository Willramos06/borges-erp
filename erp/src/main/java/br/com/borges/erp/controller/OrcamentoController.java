package br.com.borges.erp.controller;

import br.com.borges.erp.model.Orcamento;
import br.com.borges.erp.repository.OrcamentoRepository;
import br.com.borges.erp.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService service;

    @Autowired
    private OrcamentoRepository repository;

    @GetMapping
    public ResponseEntity<Page<Orcamento>> listarTodos(
            @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        Page<Orcamento> orcamentos = service.listarTodos(paginacao);
        return ResponseEntity.ok(orcamentos);
    }

    @PostMapping
    public Orcamento cadastrar(@RequestBody Orcamento orcamento) {
        return service.salvar(orcamento);
    }

    // O método buscarPorId antigo foi REMOVIDO daqui. Mantivemos apenas o novo lá embaixo.

    @GetMapping("/dashboard")
    public Map<String, BigDecimal> obterResumoDashboard(@RequestParam(required = false) Integer dias) {
        List<Orcamento> concluidos;
        
        if (dias != null && dias > 0) {
            java.time.LocalDateTime inicio = java.time.LocalDateTime.now().minusDays(dias);
            java.time.LocalDateTime fim = java.time.LocalDateTime.now();
            concluidos = repository.findByStatusContainingIgnoreCaseAndDataCriacaoBetween("Concluído", inicio, fim);
        } else {
            concluidos = repository.findByStatusContainingIgnoreCase("Concluído");
        }
        
        BigDecimal faturamento = BigDecimal.ZERO;
        BigDecimal custos = BigDecimal.ZERO;
        
        for (Orcamento o : concluidos) {
            if (o.getValorTotal() != null) {
                faturamento = faturamento.add(o.getValorTotal());
            }
            if (o.getValorSubtotal() != null) {
                custos = custos.add(o.getValorSubtotal());
            }
        }
        
        BigDecimal lucro = faturamento.subtract(custos);
        
        Map<String, BigDecimal> resumo = new HashMap<>();
        resumo.put("faturamento", faturamento);
        resumo.put("custos", custos);
        resumo.put("lucro", lucro);
        
        return resumo;
    }

    @PatchMapping("/{id}/status")
    public Orcamento atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.atualizarStatus(id, body.get("status"));
    }

    // 1. Busca um orçamento específico pelo ID (MÉTODO ÚNICO E CORRETO AGORA)
    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 2. Atualiza o orçamento existente
    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizar(@PathVariable Long id, @RequestBody Orcamento dadosAtualizados) {
        return repository.findById(id).map(orc -> {
            orc.setCliente(dadosAtualizados.getCliente());
            orc.setDescricaoResumida(dadosAtualizados.getDescricaoResumida());
            orc.setBdiPercentual(dadosAtualizados.getBdiPercentual());
            orc.setValorDesconto(dadosAtualizados.getValorDesconto()); 
            orc.setValorSubtotal(dadosAtualizados.getValorSubtotal());
            orc.setValorTotal(dadosAtualizados.getValorTotal());
            orc.setStatus(dadosAtualizados.getStatus());
            
            orc.getItens().clear();
            if (dadosAtualizados.getItens() != null) {
                orc.getItens().addAll(dadosAtualizados.getItens());
            }
            
            return ResponseEntity.ok(repository.save(orc));
        }).orElse(ResponseEntity.notFound().build());
    }
}