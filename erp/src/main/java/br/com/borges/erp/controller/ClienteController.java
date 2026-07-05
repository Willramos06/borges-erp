package br.com.borges.erp.controller;

import br.com.borges.erp.model.Cliente;
import br.com.borges.erp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping
    public List<Cliente> listarTodos() {
        // Retorna todos os clientes já ordenados alfabeticamente pela Razão Social / Nome
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nomeRazaoSocial"));
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    // NOVA ROTA: Buscar um cliente específico pelo ID para carregar no formulário de edição
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // NOVA ROTA: Atualizar os dados de um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente dadosAtualizados) {
        return repository.findById(id).map(cliente -> {
            cliente.setTipoPessoa(dadosAtualizados.getTipoPessoa());
            cliente.setNomeRazaoSocial(dadosAtualizados.getNomeRazaoSocial());
            cliente.setDocumento(dadosAtualizados.getDocumento());
            cliente.setContato(dadosAtualizados.getContato());
            cliente.setEmail(dadosAtualizados.getEmail());
            cliente.setCep(dadosAtualizados.getCep());
            cliente.setRua(dadosAtualizados.getRua());
            cliente.setNumero(dadosAtualizados.getNumero());
            cliente.setComplemento(dadosAtualizados.getComplemento()); // Novo campo
            cliente.setBairro(dadosAtualizados.getBairro());
            cliente.setCidade(dadosAtualizados.getCidade());
            cliente.setEstado(dadosAtualizados.getEstado());
            // A data de cadastro original não é alterada na atualização
            
            Cliente clienteSalvo = repository.save(cliente);
            return ResponseEntity.ok(clienteSalvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    // NOVA ROTA: Excluir um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna código 204 (Sucesso sem conteúdo)
        }
        return ResponseEntity.notFound().build(); // Retorna código 404 se não achar
    }
}