package br.sc.senac.project_pombo.controller;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.dto.PruuDTO;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.entity.Pruu;
import br.sc.senac.project_pombo.model.seletor.PruuSeletor;
import br.sc.senac.project_pombo.service.PruuService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pruu")
public class PruuController {

    @Autowired
    private PruuService pruuService;

    @PostMapping
    public ResponseEntity<Pruu> inserir(@Valid @RequestBody PruuDTO novoPruu) throws PomboException {
        return ResponseEntity.ok(pruuService.inserir(novoPruu));
    }

    @PutMapping
    public ResponseEntity<Pruu> atualizar(@Valid @RequestBody Pruu atualizar) {
        return ResponseEntity.ok(pruuService.atualizar(atualizar));
    }

    @GetMapping
    public ResponseEntity<List<Pruu>> listar() {
        return ResponseEntity.ok(pruuService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pruu> buscarPorId(@PathVariable String id) throws PomboException {
        return ResponseEntity.ok(pruuService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        pruuService.excluir(id);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Pesquisar cartas com filtros",
            description = "Retorna uma lista de cartas que atendem aos critérios especificados no seletor.")
    @PostMapping("/filtro")
    public ResponseEntity<List<Pruu>> pesquisarComSeletor(@RequestBody PruuSeletor seletor) {
        return ResponseEntity.ok(pruuService.listarComSeletor(seletor));
    }

    @Operation(summary = "Dar milhos ao pruu.",
            description = "Retorna um http 200.")
    @PostMapping("/{idPruu}/{idPombo}")
    public ResponseEntity<Void> darMilhos(@RequestBody @PathVariable String idPruu, String idPombo) throws PomboException {
        pruuService.curtidas(idPruu, idPombo);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Bloquear o pruu.",
            description = "Retornar um boolean se está bloqueado.")
    @PatchMapping("/{idPruu}/{idPombo}")
    public ResponseEntity<Boolean> bloquear(@PathVariable String idPruu, String idPombo) throws PomboException {
          return ResponseEntity.ok(pruuService.bloquear(idPruu, idPombo));
    }








}
