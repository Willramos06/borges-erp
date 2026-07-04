package br.com.borges.erp.repository;

import br.com.borges.erp.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List; // Importação adicionada para corrigir o erro

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    @Query("SELECT MAX(o.sequencial) FROM Orcamento o WHERE o.ano = :ano")
    Integer findMaxSequencialByAno(@Param("ano") Integer ano);

    // Busca orçamentos filtrando pelo status, ignorando maiúsculas e minúsculas
    List<Orcamento> findByStatusContainingIgnoreCase(String status);
    // Busca orçamentos filtrando pelo status e por um período de tempo
    List<Orcamento> findByStatusContainingIgnoreCaseAndDataCriacaoBetween(String status, java.time.LocalDateTime inicio, java.time.LocalDateTime fim);
}