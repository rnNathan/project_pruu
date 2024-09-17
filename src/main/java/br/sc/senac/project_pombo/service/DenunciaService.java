package br.sc.senac.project_pombo.service;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.dto.DenunciaDTO;
import br.sc.senac.project_pombo.model.entity.Denuncia;
import br.sc.senac.project_pombo.model.entity.PerfilAcesso;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.entity.Pruu;
import br.sc.senac.project_pombo.model.repository.DenunciaRepository;
import br.sc.senac.project_pombo.model.repository.PomboRepository;
import br.sc.senac.project_pombo.model.repository.PruuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;
    @Autowired
    private PruuRepository pruuRepository;
    @Autowired
    private PomboRepository pomboRepository;



    public Denuncia criarDenuncia(DenunciaDTO denunciaDto) throws PomboException {
        Pombo pombo = pomboRepository.findById(denunciaDto.getIdPombo()).orElseThrow(() -> new PomboException("Pombo não encontrado"));
        Pruu pruu = pruuRepository.findById(denunciaDto.getIdMensagem()).orElseThrow(() -> new PomboException("Mensagem não encontrada."));
        Denuncia denuncia = new Denuncia();

        denuncia.setDescricaoDenuncia(denunciaDto.getDescricaoDenuncia());
        denuncia.setPombo_id(pombo);
        denuncia.setPruu_id(pruu);

        return denunciaRepository.save(denuncia);

    }

    public List<Denuncia> listarTodasDenuncias(String ehAdmin) throws PomboException {
        Pombo pombo = pomboRepository.findById(ehAdmin).orElseThrow(() -> new PomboException("Pombo não encontrado"));
        this.verificarSeEhAdmin(pombo.getId());
        return denunciaRepository.findAll();
    }

    public Denuncia consultarDenunciaPorId(String ehAdmin) throws PomboException {
        this.verificarSeEhAdmin(ehAdmin);
        return denunciaRepository.findById(ehAdmin).get();
    }

    private void verificarSeEhAdmin(String pombinho) throws PomboException {
        Pombo pombo = pomboRepository.findById(pombinho).orElseThrow(() -> new PomboException("Usuário não encontrado."));

        if(pombo.getPerfilAcesso() == PerfilAcesso.USUARIO) {
            throw new PomboException("Usuário não autorizado.");
        }
    }
}
