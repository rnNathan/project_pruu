package br.sc.senac.project_pombo.controller;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Denuncia;
import br.sc.senac.project_pombo.service.DenunciaService;
import br.sc.senac.project_pombo.service.PruuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController {

    @Autowired
    private DenunciaService denunciaService;

    @PostMapping
    public ResponseEntity<Denuncia> cadastrarDenuncia(Denuncia cadastrarDenuncia) {
        return ResponseEntity.ok(denunciaService .inserir(cadastrarDenuncia));
    }

    @GetMapping
    public ResponseEntity<List<Denuncia>> listarTodasAsDenuncias() throws PomboException {
        return ResponseEntity.ok(denunciaService.listarTodasDenuncias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> consultarDenunciaPorId(@PathVariable String idDenuncia) throws PomboException {
         return ResponseEntity.ok(denunciaService.consultarDenunciaPorId(idDenuncia));
    }



}
