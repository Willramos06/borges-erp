package br.com.borges.erp.controller;

import br.com.borges.erp.model.Configuracao;
import br.com.borges.erp.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;  // ... (outros imports)

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/configuracoes")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository repository;

    @GetMapping
    public Configuracao obterConfiguracao() {
        return repository.findById(1L).orElseGet(() -> {
            Configuracao c = new Configuracao();
            c.setId(1L);
            c.setNomeEmpresa("Construtora Borges Solucoes Ltda");
            c.setCnpj("00.000.000/0001-00");
            c.setEndereco("Rua Principal, 100");
            c.setCidade("Caratinga");
            c.setEstado("MG");
            c.setBdiPadrao(new BigDecimal("15.00"));
            return repository.save(c);
        });
    }

   

    @PutMapping
    public Configuracao atualizarConfiguracao(@Valid @RequestBody Configuracao config) {
        config.setId(1L); 
        return repository.save(config);
    }
}