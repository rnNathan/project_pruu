package br.sc.senac.project_pombo.service;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.dto.PruuDTO;
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

    public Pruu inserir(PruuDTO pruuDTO) throws PomboException {
        Pombo pombo = pomboRepository.findById(pruuDTO.getIdPombo()).orElseThrow(() -> new PomboException("Pombo não encontrado."));

        Pruu pruu = new Pruu();

        pruu.setPombo(pombo);
        pruu.setMensagem(pruuDTO.getMensagem());

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

    public void curtidas(String idPruu, String idPombo) throws PomboException {
        Pruu pruu = pruuRepository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não encontrado."));
        Pombo pombo = pomboRepository.findById(idPombo).orElseThrow(() -> new PomboException("Pombo não encontrado."));

        List<Pombo> milhos = pruu.getMilhos();

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
        this.verificarSeEhAdmin(idPombo); // Verifica se existe um usuário.
        Pruu pruu = pruuRepository.findById(idPombo).get(); //pegando a mensagem.
        Pombo pombo = pomboRepository.findById(idPruu).get(); // pegando o usuário.
        boolean sucesso = false; //retorno

        if (pruu.getId() == null) { //
            throw new PomboException("Mensagem não encontrada");
        } else if (pombo.getPerfilAcesso() == PerfilAcesso.USUARIO) { //verificando perfil de acesso.
            throw new PomboException("Não pode bloquear pois não é administrador, usuários pode apenas denunciar.");
        } else if (pruu.getBloqueado() == false) { //Caso a mensagem esteja em false, transforme em true.
            pruu.setBloqueado(true); //setando manualmente.
            sucesso = true;
        }

        return sucesso;

    }

    private void verificarSeEhAdmin(String pombinho) throws PomboException {
        Pombo pombo = pomboRepository.findById(pombinho).orElseThrow(() -> new PomboException("Usuário não encontrado."));

        if(pombo.getPerfilAcesso() == PerfilAcesso.USUARIO) {
            throw new PomboException("Usuário não autorizado.");
        }
    }
}
