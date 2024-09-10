package br.sc.senac.project_pombo.controller;

import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.service.PomboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pombo")
public class PomboController {

    @Autowired
    private PomboService service;

    @PostMapping
    public ResponseEntity<Pombo> inserir(@Valid @RequestBody Pombo pombo) {
        return ResponseEntity.ok(service.inserir(pombo));
    }

    @PutMapping
    public ResponseEntity<Pombo> atualizar(@Valid @RequestBody Pombo pombo) {
        return ResponseEntity.ok(service.atualizar(pombo));
    }

    @GetMapping
    public ResponseEntity<List<Pombo>> consultarTodos() {
        return ResponseEntity.ok(service.consultarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pombo> consultarPorId(@PathVariable String id) throws PomboException {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }



}
