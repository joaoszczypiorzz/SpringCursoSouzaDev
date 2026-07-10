package br.com.joaoszczypior.spring_boot_essentials.web;

import br.com.joaoszczypior.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.joaoszczypior.spring_boot_essentials.dtos.ProdutoDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import br.com.joaoszczypior.spring_boot_essentials.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoEntity>> findAll () {
        List<ProdutoEntity> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProdutoDto dto){
        ProdutoEntity prod = produtoService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Integer id, @RequestBody ProdutoDto dto) throws NotFoundException {
        produtoService.updateProduto(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtoService.deleteProduto(id);
    }

}
