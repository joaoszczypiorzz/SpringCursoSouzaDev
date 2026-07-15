package br.com.joaoszczypior.spring_boot_essentials.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlunoDto {
    @NotBlank
    private String nome;
    @Email (message = "E-mail informado inválido!")
    private String email;
}
