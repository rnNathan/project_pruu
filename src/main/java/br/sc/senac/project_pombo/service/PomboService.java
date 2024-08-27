package br.sc.senac.project_pombo.service;

import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.repository.PomboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomboService {

    @Autowired
    private PomboRepository repository;

    private Pombo inserir(Pombo novoPombo) {
        return repository.save(novoPombo);
    }

    public Pombo atualizar(Pombo atualizarPombo) {
        return repository.save(atualizarPombo);
    }

    public Pombo consultarPorId(String id) throws PomboException {
        return repository.findById(id).orElseThrow(() -> new PomboException("Pombo não encontrado"));
    }

    public List<Pombo> ConsultarTodos() {
        return repository.findAll();
    }

}
