package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.AlunosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.TreinosEntity;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAlunosRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.IAvalicacoesFisicasRepository;
import br.com.joaoszczypior.spring_boot_essentials.database.model.repository.ITreinosRepository;
import br.com.joaoszczypior.spring_boot_essentials.dtos.AlunoDto;
import br.com.joaoszczypior.spring_boot_essentials.dtos.AlunoListDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final IAlunosRepository alunosRepository;
    private final IAvalicacoesFisicasRepository avalicacoesFisicasRepository;
    private final ITreinosRepository treinosRepository;

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

    /**
     * Função para buscar todos os alunos cadastrados no banco e transforma-los em Dtos
     * A serem repassadas ao usuário da API
     * @return uma List de Aluno DTO
     * @Author João Szczypior
     */
    public List<AlunoListDto> findAll () {
        return alunosRepository.findAll().stream()
                .map(a -> new AlunoListDto(a.getId(), a.getNome(), a.getEmail())).toList();
    }

    /**
     * Função que deleta um aluno do Banco, e seus Relacionamentos remanescentes
     * Excluindo tanto os treinos desse Aluno, quanto suas avaliações fisicas.
     * @param alunoId id do Aluno a ser deletado
     * @throws NotFoundException Not found caso aluno não for encontrado com o id informado
     * @Author João Szczypior
     */
    // Usando transactional para garantir que tudo ocorra em apenas uma única transação no Banco
    // E em caso de Algum erro o Spring rode um Rollback e garanta integridade nos dados
    // Com a anotação rollbackFor apontando para a classe Pai de todas as exceptions
    // Me garante que qualquer exception lançada aqui o rolback será executado
    @Transactional(rollbackFor = Exception.class)
    public void deletarAluno(Integer alunoId) throws NotFoundException {
        AlunosEntity aluno = alunosRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado!"));

        //1. deletar treinos relacionados com esse Aluno (Evitando erro de Constraint quebrada)
        List<Integer> treinosAlunoIds = aluno.getTreinos().stream()
                .map(TreinosEntity::getId)
                .toList();
        treinosRepository.deleteAllById(treinosAlunoIds);

        //2. Deletar o aluno
        alunosRepository.deleteById(aluno.getId());

        //3. deletar as avaliações fisicas vinculadas a esse aluno
        avalicacoesFisicasRepository.deleteById(aluno.getAvalicaoFisica().getId());
    }
}
