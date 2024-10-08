package br.sc.senac.project_pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.Set;

@Table(name = "TB_POMBO")
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
    @Enumerated(EnumType.STRING)
    private PerfilAcesso perfilAcesso;

    @JsonBackReference
    @OneToMany(mappedBy = "pombo")
    private List<Pruu> mensagens; // lista de mensagens.

}
