package br.com.borges.erp.repository;

import br.com.borges.erp.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}