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
@CrossOrigin(origins = "*")
public class OrcamentoController {

    @Autowired
    private OrcamentoService service;

    @Autowired
    private OrcamentoRepository repository;

    @GetMapping
    public ResponseEntity<Page<Orcamento>> listarTodos(
            @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        
        // CORREÇÃO: O nome correto da variável aqui é 'service'
        Page<Orcamento> orcamentos = service.listarTodos(paginacao);
        return ResponseEntity.ok(orcamentos);
    }

    @PostMapping
    public Orcamento cadastrar(@RequestBody Orcamento orcamento) {
        return service.salvar(orcamento);
    }

    @GetMapping("/{id}")
    public Orcamento buscarPorId(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

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
}