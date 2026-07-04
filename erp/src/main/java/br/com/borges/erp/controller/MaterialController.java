package br.com.borges.erp.controller;

import br.com.borges.erp.model.Material;
import br.com.borges.erp.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiais")
@CrossOrigin(origins = "*")
public class MaterialController {

    @Autowired
    private MaterialRepository repository;

    @GetMapping
    public List<Material> listarTodos() {
        // Retorna a lista sempre em ordem alfabética pela descrição
        return repository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
    }

    @PostMapping
    public Material cadastrar(@RequestBody Material material) {
        return repository.save(material);
    }

    // NOVA ROTA: Buscar um material específico para edição
    @GetMapping("/{id}")
    public ResponseEntity<Material> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // NOVA ROTA: Atualizar os dados de um material existente
    @PutMapping("/{id}")
    public ResponseEntity<Material> atualizar(@PathVariable Long id, @RequestBody Material dadosAtualizados) {
        return repository.findById(id).map(material -> {
            material.setDescricao(dadosAtualizados.getDescricao());
            material.setValorCusto(dadosAtualizados.getValorCusto());
            material.setValorVenda(dadosAtualizados.getValorVenda());
            material.setEstoqueAtual(dadosAtualizados.getEstoqueAtual());
            
            Material materialSalvo = repository.save(material);
            return ResponseEntity.ok(materialSalvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    // NOVA ROTA: Excluir um material do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}