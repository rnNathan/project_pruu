package br.sc.senac.project_pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PruuDTO {

    private String idPombo;
    private String mensagem;

}
