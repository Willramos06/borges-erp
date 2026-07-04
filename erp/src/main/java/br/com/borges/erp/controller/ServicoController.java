package br.com.borges.erp.controller;

import br.com.borges.erp.model.Servico;
import br.com.borges.erp.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;

    @GetMapping
    public List<Servico> listarTodos() {
        // Retorna a lista sempre em ordem alfabética pela descrição
        return repository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
    }

    @PostMapping
    public Servico cadastrar(@RequestBody Servico servico) {
        return repository.save(servico);
    }

    // NOVA ROTA: Buscar um serviço específico para edição
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // NOVA ROTA: Atualizar os dados de um serviço existente
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @RequestBody Servico dadosAtualizados) {
        return repository.findById(id).map(servico -> {
            servico.setDescricao(dadosAtualizados.getDescricao());
            servico.setValorCusto(dadosAtualizados.getValorCusto());
            servico.setValorVenda(dadosAtualizados.getValorVenda());
            
            Servico servicoSalvo = repository.save(servico);
            return ResponseEntity.ok(servicoSalvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    // NOVA ROTA: Excluir um serviço do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}