package br.com.joaoszczypior.spring_boot_essentials.service;

import br.com.joaoszczypior.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.joaoszczypior.spring_boot_essentials.dtos.ProdutoDto;
import br.com.joaoszczypior.spring_boot_essentials.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    private static final List<ProdutoEntity> PRODUTOS = new ArrayList<>();

    static {
        PRODUTOS.add(ProdutoEntity.builder()
                .id(1)
                .nome("Notebook")
                .preco(new BigDecimal(5000))
                .quantidade(10)
                .descricao("Note book Lindo e Boladão")
                .build());
        PRODUTOS.add( ProdutoEntity.builder()
                .id(2)
                .nome("Iphone")
                .preco(new BigDecimal(7000))
                .quantidade(10)
                .descricao("Iphone 13 do João Vitor")
                .build());
        PRODUTOS.add(ProdutoEntity.builder()
                .id(3)
                .nome("Mouse")
                .quantidade(10)
                .preco(new BigDecimal(500))
                .descricao("Mouse Gamer pichau Force")
                .build());
    }

    public List<ProdutoEntity> findAll () {
        return new ArrayList<ProdutoEntity>(PRODUTOS);
    }

    public ProdutoEntity create (ProdutoDto produtoDto) {
        Integer identificador = PRODUTOS.stream()
                .mapToInt(ProdutoEntity::getId)
                .max()
                .orElse(0) + 1;

        ProdutoEntity novoProd = ProdutoEntity.builder()
                .id(identificador)
                .nome(produtoDto.getNome())
                .descricao(produtoDto.getDescricao())
                .quantidade(produtoDto.getQuantidade())
                .preco(produtoDto.getPreco())
                .build();
        PRODUTOS.add(novoProd);

        return novoProd;
    }

    public ProdutoEntity updateProduto (ProdutoDto dto, Integer id) throws NotFoundException {
        ProdutoEntity produto = PRODUTOS.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        return produto;
    }

    public void deleteProduto(Integer id) {
        PRODUTOS.removeIf(p -> p.getId().equals(id));
    }
}
