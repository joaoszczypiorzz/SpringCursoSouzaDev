package br.com.joaoszczypior.spring_boot_essentials.web;

import br.com.joaoszczypior.spring_boot_essentials.dtos.TreinoDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.BadRequestException;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import br.com.joaoszczypior.spring_boot_essentials.service.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/treinos")
@Validated
public class TreinosController {

    private final TreinoService treinoService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTreino (@Valid @RequestBody TreinoDto treinoDto) throws NotFoundException, BadRequestException {
        treinoService.saveTreino(treinoDto);
    }
}
