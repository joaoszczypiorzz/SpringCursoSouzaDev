package br.com.joaoszczypior.spring_boot_essentials.web;


import br.com.joaoszczypior.spring_boot_essentials.dtos.AvaliacaoFisicaDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import br.com.joaoszczypior.spring_boot_essentials.service.AvaliacaoFisicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/avaliacoesFisicas")
@RequiredArgsConstructor
public class AvaliacoesFisicasController {

    private final AvaliacaoFisicaService avaliacaoFisicaService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAvaliacaoFisica(@RequestBody AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
        avaliacaoFisicaService.criarAvaliacaoFisica(avaliacaoFisicaDto);
    }
}
