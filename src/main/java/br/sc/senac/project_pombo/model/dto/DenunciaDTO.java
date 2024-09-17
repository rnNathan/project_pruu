package br.sc.senac.project_pombo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DenunciaDTO {

    private String id;
    private String idPombo;
    private String idMensagem;
    private String descricaoDenuncia;


}
