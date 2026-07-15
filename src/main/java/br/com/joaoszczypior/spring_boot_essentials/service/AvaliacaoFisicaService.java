package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.AlunosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAlunosRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAvalicacoesFisicasRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.AvaliacaoFisicaDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AvaliacaoFisicaService {

    private final IAlunosRepository alunosRepository;
    private final IAvalicacoesFisicasRepository avalicacoesFisicasRepository;

    public void criarAvaliacaoFisica (AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
        AlunosEntity aluno = alunosRepository.findById(avaliacaoFisicaDto.getAlunoId())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado!"));

        AvaliacoesFisicasEntity avalicaoFisica = aluno.getAvalicaoFisica();
        if(avalicaoFisica != null) {
            throw new BadRequestException("Avaliação fisica já cadastrada para este aluno!");
        }

        avalicaoFisica = AvaliacoesFisicasEntity.builder()
                .peso(avaliacaoFisicaDto.getPeso())
                .altura(avaliacaoFisicaDto.getAltura())
                .porcentagemGorduraCorporal(avaliacaoFisicaDto.getPorcentagemGorduraCorporal())
                .build();

        aluno.setAvalicaoFisica(avalicaoFisica);
        alunosRepository.save(aluno);
    }
}
