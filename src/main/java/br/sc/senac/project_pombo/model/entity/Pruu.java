package br.sc.senac.project_pombo.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;


@Table (name = "TB_MENSAGEM")
@Entity
@Data
public class Pruu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Pombo idUsuario;

    @NotBlank
    @Size(min = 1, max = 300)
    private String mensagem;

    @CreationTimestamp
    @NotNull
    private LocalDate dataCriada;

    @OneToMany(mappedBy = "mensagens")
    private Set<Pombo> curtidas;








}
