package br.com.joaoszczypior.spring_boot_essentials.database.model.repository;

import br.com.joaoszczypior.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAvalicacoesFisicasRepository extends JpaRepository<AvaliacoesFisicasEntity, Integer> {
}
