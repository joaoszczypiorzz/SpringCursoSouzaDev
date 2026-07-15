package br.com.joaoszczypior.spring_boot_essentials.database.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "nome")
    private String nome;
    @Column(nullable = false, unique = true, name = "email")
    private String email;

    // O cascade serve para todas as vezes que eu realizar uma modificação
    // neste relacionamento o Hibernate sabe que deve atualizar as informações
    // na entidade referenciada
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avaliacao_fisica_id")
    private AvaliacoesFisicasEntity avalicaoFisica;

    @OneToMany(mappedBy = "aluno")
    private Set<TreinosEntity> treinos = new HashSet<>();
}
