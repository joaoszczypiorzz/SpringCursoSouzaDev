package br.com.joaoszczypior.spring_boot_essentials.web;


import br.com.joaoszczypior.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.joaoszczypior.spring_boot_essentials.dtos.AlunoDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import br.com.joaoszczypior.spring_boot_essentials.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
@Validated
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody AlunoDto dto) throws BadRequestException {
        alunoService.create(dto);
    }

    @GetMapping("/{alunoId}/avaliacao")
    public AvaliacoesFisicasEntity getAvaliacao(@PathVariable Integer alunoId) throws NotFoundException, BadRequestException {
        return alunoService.getAlunoAvaliacao(alunoId);
    }
}
