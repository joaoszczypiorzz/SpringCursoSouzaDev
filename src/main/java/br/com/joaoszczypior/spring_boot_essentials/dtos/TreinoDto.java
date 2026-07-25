package br.com.joaoszczypior.spring_boot_essentials.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TreinoDto {
    @NotNull(message = "É obrigatório informar o Aluno")
    private Integer alunoId;
    @NotBlank
    private String nome;
    @NotEmpty
    private List<Integer> exerciciosIds;
}
