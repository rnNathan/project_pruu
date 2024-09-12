package br.sc.senac.project_pombo.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "pruu_id")
    private Pruu pruu_id;

    @ManyToOne
    @JoinColumn(name = "pombo_id")
    private Pombo pombo_id;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 300)
    private String descricaoDenuncia;


}
