package br.com.joaoszczypior.spring_boot_essentials.database.model.repository;

import br.com.joaoszczypior.spring_boot_essentials.database.model.ExerciciosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExerciciosRepository extends JpaRepository<ExerciciosEntity, Integer> {
    List<ExerciciosEntity> findAllByGrupoMuscular(String grupoMuscular);
}
