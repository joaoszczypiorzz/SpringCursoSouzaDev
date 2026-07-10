package br.com.joaoszczypior.spring_boot_essentials.dtos;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProdutoDto {

    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Integer quantidade;
}
