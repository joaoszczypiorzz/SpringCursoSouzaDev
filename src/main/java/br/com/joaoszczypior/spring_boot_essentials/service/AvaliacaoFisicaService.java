package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.AlunosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAlunosRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAvalicacoesFisicasRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.AvaliacaoFisicaDto;
import br.com.joaoszczypior.spring_boot_essentials.dtos.projections.AvaliacoesFisicasProjection;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


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

    /**
     * Função que busca Todas as Avaliações Fisicas cadastradas no Banco
     * Puxando apenas os atributos especificados na minha Projection
     * @return Uma Lista com todas as Avalições fisicas que existirem no Banco
     * @Author João Szczypior
     */
    public List<AvaliacoesFisicasProjection> getAllAvaliacoes () {
        return avalicacoesFisicasRepository.getAllAvaliacoes();
    }

    /**
     * Função que Busca as Avaliações fisicas do Banco de forma paginada e
     * Mantendo os atributos que serão buscados especificados na minha Projection
     * @param page Número da Pagina de Busca
     * @param size quantos elementos serão buscados por vez
     * @return Uma lista das avaliações Paginadas (Evitando buscar todas as informações de uma vez só do BD)
     * @Author João Szczypior
     */
    public Page<AvaliacoesFisicasProjection> getAllAvaliacoesPageable (Integer page, Integer size) {
        return avalicacoesFisicasRepository.getAllAvaliacoesPage(PageRequest.of(page, size));
    }
}
