package br.com.joaoszczypior.spring_boot_essentials.database.model.repository;

import br.com.joaoszczypior.spring_boot_essentials.database.model.TreinosEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITreinosRepository extends JpaRepository<TreinosEntity, Integer> {
}
