package br.com.joaoszczypior.spring_boot_essentials.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProdutoEntity {
    private Integer id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Integer quantidade;
}
