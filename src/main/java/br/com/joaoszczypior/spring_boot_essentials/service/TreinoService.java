package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.AlunosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.TreinosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAlunosRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IExerciciosRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.ITreinosRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.TreinoDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final IAlunosRepository alunosRepository;
    private final IExerciciosRepository exerciciosRepository;
    private final ITreinosRepository treinosRepository;

    public void saveTreino (TreinoDto dto)  throws NotFoundException, BadRequestException{
        Set<ExerciciosEntity> exercicios = new HashSet<>();
        AlunosEntity aluno = alunosRepository.findById(dto.getAlunoId())
                .orElseThrow( () -> new NotFoundException("Aluno não encontrado"));

        TreinosEntity treino = treinosRepository.findByNomeAndAlunoId(dto.getNome(), dto.getAlunoId())
                .orElse(null);

        if(treino != null) {
            throw new BadRequestException("Já existe um treino com esse nome para esse Aluno!");
        }

        // For que realiza a validação na List de ids de Exercicios enviadas na dto
        for(Integer exercicioId : dto.getExerciciosIds()) {
            ExerciciosEntity exercicio = exerciciosRepository.findById(exercicioId)
                    .orElseThrow(() -> new NotFoundException(String.format("Exercício %s não Encontrado", exercicioId)));

            exercicios.add(exercicio);
        }

        treino = TreinosEntity.builder()
                .nome(dto.getNome())
                .aluno(aluno)
                .exercicios(exercicios)
                .build();

        treinosRepository.save(treino);
    }

}
