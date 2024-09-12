package br.sc.senac.project_pombo.service;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.PerfilAcesso;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.entity.Pruu;
import br.sc.senac.project_pombo.model.repository.PomboRepository;
import br.sc.senac.project_pombo.model.repository.PruuRepository;
import br.sc.senac.project_pombo.model.seletor.PruuSeletor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PruuService {

    @Autowired
    private PruuRepository pruuRepository;
    @Autowired
    private PomboRepository pomboRepository;

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
        if (seletor.temPaginacao()) {
            int pageNumber = seletor.getPagina();
            int pageSize = seletor.getLimite();
            PageRequest pagina = PageRequest.of(pageNumber - 1, pageSize);
            return pruuRepository.findAll(seletor, pagina).toList();
        }
        //https://www.baeldung.com/spring-data-jpa-query-by-example
        return pruuRepository.findAll(seletor);
    }

    public void curtidas(String idPombo, String idPruu) {
        Pruu pruu = pruuRepository.findById(idPombo).get();
        Pombo pombo = pomboRepository.findById(idPruu).get();
        List <Pombo> milhos = pruu.getMilhos();

        if (milhos.contains(pombo)) {
            milhos.remove(pombo);
            pruu.setTotalDeMilhos(pruu.getTotalDeMilhos() - 1);
        } else {
            milhos.add(pombo);
            pruu.setTotalDeMilhos(pruu.getTotalDeMilhos() + 1);
        }
        pruu.setMilhos(milhos);
        pruuRepository.save(pruu);
    }

    public boolean bloquear(String idPombo, String idPruu) throws PomboException {
        this.verificarSeEhAdmin(idPombo);
        Pruu pruu = pruuRepository.findById(idPombo).get();
        Pombo pombo = pomboRepository.findById(idPruu).get();
        boolean sucesso = false;

        if (pruu.getId() == null) {
            throw new PomboException("Mensagem não encontrada");
        } else if (pombo.getPerfilAcesso() == PerfilAcesso.USUARIO) {
            throw new PomboException("Não pode bloquear pois não é administrador, usuários pode apenas denunciar.");
        } else if (pruu.getBloqueado() == false){
           pruu.setBloqueado(true);
           sucesso = true;
        }

        return sucesso;

    }

    private void verificarSeEhAdmin(String idPombo) throws PomboException {
        Pombo pombo = pomboRepository.findById(idPombo).get();
        if (pombo.getId() == null) {
            throw new PomboException("Pombo não encontrado");
        }else if(pombo.getPerfilAcesso() == PerfilAcesso.USUARIO) {
            throw new PomboException("Pombo não é administrador");
        }
    }
}
