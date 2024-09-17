package br.sc.senac.project_pombo.service;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Denuncia;
import br.sc.senac.project_pombo.model.entity.PerfilAcesso;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.repository.DenunciaRepository;
import br.sc.senac.project_pombo.model.repository.PomboRepository;
import br.sc.senac.project_pombo.model.repository.PruuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {

    @Autowired
    DenunciaRepository denunciaRepository;
    @Autowired
    private PruuRepository pruuRepository;
    @Autowired
    private PomboRepository pomboRepository;

    public Denuncia inserir(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }

    public List<Denuncia> listarTodasDenuncias() throws PomboException {
        Pombo pombo = new Pombo();
        this.verificarSeEhAdmin(pombo.getId());
        return denunciaRepository.findAll();
    }

    public Denuncia consultarDenunciaPorId(String ehAdmin) throws PomboException {
        this.verificarSeEhAdmin(ehAdmin);
        return denunciaRepository.findById(ehAdmin).get();
    }

    private void verificarSeEhAdmin(String idPombo) throws PomboException {
        Pombo pombo = pomboRepository.findById(idPombo).orElseThrow(() -> new PomboException("Usuário não encontrado."));

        if(pombo.getPerfilAcesso() == PerfilAcesso.USUARIO) {
            throw new PomboException("Usuário não autorizado.");
        }
    }
}
