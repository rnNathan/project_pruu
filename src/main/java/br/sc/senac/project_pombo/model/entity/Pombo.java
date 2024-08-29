package br.sc.senac.project_pombo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Table(name = "TB_USUARIO")
@Entity
@Data
public class Pombo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotEmpty
    @Size(min = 3, max = 40)
    private String nome;

    @Email
    @NotEmpty
    @Size(max = 200)
    private String email;

    @NotNull
    @NotBlank
    @CPF
    private String cpf;

    @NotNull
    private PerfilAcesso perfilAcesso;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Pruu> mensagens;


}
