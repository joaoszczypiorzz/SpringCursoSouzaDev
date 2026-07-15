package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.AlunosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAlunosRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.AlunoDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final IAlunosRepository alunosRepository;

    @Transactional
    public void create(AlunoDto dto) throws BadRequestException {
        AlunosEntity alu =  alunosRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if(alu != null) {
            throw new BadRequestException("E-mail já cadastrado no sistema!");
        }

        AlunosEntity aluno = AlunosEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .build();
        alunosRepository.save(aluno);
    }

    public AvaliacoesFisicasEntity getAlunoAvaliacao (Integer alunoId) throws NotFoundException, BadRequestException {
        AlunosEntity aluno = alunosRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado!"));

        AvaliacoesFisicasEntity avaliacao = aluno.getAvalicaoFisica();
        if(avaliacao == null) {
            throw new BadRequestException("Aluno ainda não possui Avaliações fisicas cadastradas");
        }
        return avaliacao;
    }
}
