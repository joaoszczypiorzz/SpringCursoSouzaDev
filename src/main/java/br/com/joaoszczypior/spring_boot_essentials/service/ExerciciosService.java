package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IExerciciosRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.ExercicioDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciciosService {

    private final IExerciciosRepository exerciciosRepository;

    public List<ExerciciosEntity> findAll() {
        return exerciciosRepository.findAll();
    }

    @Transactional
    public void create(ExercicioDto dto) {
        exerciciosRepository.save(ExerciciosEntity.builder()
                        .nome(dto.getNome())
                        .grupoMuscular(dto.getGrupoMuscular())
                .build());
    }

    public List<ExerciciosEntity> getExerciciosByGrupMuscular(String grupoMuscular) {
        return exerciciosRepository.findAllByGrupoMuscular(grupoMuscular);
    }
}
