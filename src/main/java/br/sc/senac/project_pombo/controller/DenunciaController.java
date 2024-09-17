package br.sc.senac.project_pombo.controller;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.dto.DenunciaDTO;
import br.sc.senac.project_pombo.model.entity.Denuncia;
import br.sc.senac.project_pombo.service.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController {

    @Autowired
    private DenunciaService denunciaService;

    @PostMapping("/realizar")
    public ResponseEntity<Denuncia> cadastrarDenuncia(@RequestBody DenunciaDTO denunciaDto) throws PomboException {
        return ResponseEntity.ok(denunciaService.criarDenuncia(denunciaDto));
    }

    @GetMapping("/listarDenuncia/{id}")
    public ResponseEntity<List<Denuncia>> listarTodasAsDenuncias(String id) throws PomboException {
        return ResponseEntity.ok(denunciaService.listarTodasDenuncias(id));
    }

    @GetMapping("/{idDenuncia}")
    public ResponseEntity<Denuncia> consultarDenunciaPorId(@PathVariable String idDenuncia) throws PomboException {
         return ResponseEntity.ok(denunciaService.consultarDenunciaPorId(idDenuncia));
    }



}
