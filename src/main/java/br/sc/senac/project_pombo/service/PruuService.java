package br.sc.senac.project_pombo.service;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Pruu;
import br.sc.senac.project_pombo.model.repository.PruuRepository;
import br.sc.senac.project_pombo.model.seletor.PruuSeletor;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PruuService {

    @Autowired
    private PruuRepository pruuRepository;

    public Pruu inserir(Pruu pruu) {
        return pruuRepository.save(pruu);
    }

    public Pruu atualizar(Pruu pruu) {
        return pruuRepository.save(pruu);
    }

    public List<Pruu> listar() {
        return pruuRepository.findAll();
    }

    public Pruu buscarPorId(String id) throws PomboException {
        return pruuRepository.findById(id).orElseThrow(() -> new PomboException("Pruu não encontrado."));
    }

    public boolean excluir(String id) {
        pruuRepository.deleteById(id);
        return true;
    }
    public List<Pruu> listarComSeletor(PruuSeletor seletor) {
        if(seletor.temPaginacao()) {
            int pageNumber = seletor.getPagina();
            int pageSize = seletor.getLimite();
            PageRequest pagina = PageRequest.of(pageNumber - 1, pageSize);
            return pruuRepository.findAll(seletor, pagina).toList();
        }
        //https://www.baeldung.com/spring-data-jpa-query-by-example
        return pruuRepository.findAll(seletor);
    }












}
