package br.sc.senac.project_pombo.service;

import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.entity.Pruu;
import br.sc.senac.project_pombo.model.repository.PomboRepository;
import br.sc.senac.project_pombo.model.repository.PruuRepository;
import br.sc.senac.project_pombo.model.seletor.PomboSeletor;
import br.sc.senac.project_pombo.model.seletor.PruuSeletor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomboService {

    @Autowired
    private PomboRepository repository;

    @Autowired
    private PruuRepository pruuRepository;


    public Pombo inserir(Pombo novoPombo) throws PomboException {
        List<Pombo> listaDePombos = repository.findAll();
        for (Pombo pombo : listaDePombos) {
            if(pombo.getCpf().equals(novoPombo.getCpf())) {
                throw new PomboException("Tem pombo usando esse CPF, por favor, saia antes de levar uma cagada");
            }
            if (pombo.getEmail().equals(novoPombo.getEmail())) {
                throw new PomboException("Tem pombo usando esse EMAIL, por favor, saia antes de levar uma cagada");
            }
        }

        return repository.save(novoPombo);
    }

    public Pombo atualizar(Pombo atualizarPombo) {
        return repository.save(atualizarPombo);
    }

    public Pombo consultarPorId(String id) throws PomboException {
        return repository.findById(id).orElseThrow(() -> new PomboException("Pombo não encontrado."));
    }

    public List<Pombo> consultarTodos() {
        return repository.findAll();
    }

    public boolean excluir(String id) throws PomboException {
       Pombo pombo = repository.findById(id).get();
        if (pombo.getMensagens() != null) {
            throw new PomboException("Quer abandonar o movimento dos Pombo? Apague os Pruus antes de ir.");
        }
        repository.deleteById(id);
        return true;
    }

    public List<Pombo> listarComSeletor(PomboSeletor seletor) {
        if (seletor.temPaginacao()) {
            int pageNumber = seletor.getPagina();
            int pageSize = seletor.getLimite();
            PageRequest pagina = PageRequest.of(pageNumber - 1, pageSize);
            return repository.findAll(seletor, pagina).toList();
        }
        //https://www.baeldung.com/spring-data-jpa-query-by-example
        return repository.findAll(seletor);
    }

}
