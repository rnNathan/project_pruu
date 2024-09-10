package br.sc.senac.project_pombo.service;


import br.sc.senac.project_pombo.exception.PomboException;
import br.sc.senac.project_pombo.model.entity.Pombo;
import br.sc.senac.project_pombo.model.entity.Pruu;
import br.sc.senac.project_pombo.model.repository.PomboRepository;
import br.sc.senac.project_pombo.model.repository.PruuRepository;
import br.sc.senac.project_pombo.model.seletor.PruuSeletor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

//    public List<Pruu> curtidas(String idUsuario) {
//        Set<List>
//
//
//        return null;
//    }

    public Optional<Pruu> likeOrDislike(String idPruu, String idUsuario) throws PomboException {
        //Pegar o Pruu pelo id dele.
        Optional<Pruu> pruuMensagem = pruuRepository.findById(idPruu);

        Pruu pruu;
        Pombo pombo = null;
        //isPresent vê se encontrou algum valor, se encontrar irei poder mexer,
        //Caso ao contrário, ele envia uma mensagem de não encontrada.

        if (pruuMensagem.isPresent()) {
            pruu = pruuMensagem.get();
            Set<Pombo> pombosQueCurtiram = pruu.getMilhos();

            //Caso o pombo deu deslike, ele vai retirar da lista.
            if (pombosQueCurtiram.contains(idUsuario)) {
                pombosQueCurtiram.remove(idUsuario);
                pruu.setTotalDeMilhos(pruu.getTotalDeMilhos() - 1);
            } else {
                //Caso contrário, de like
                PomboRepository pomboRepo = pomboRepository;
                pomboRepo.findById(idUsuario);

                pombosQueCurtiram.add(pombo);
                pruu.setTotalDeMilhos(pruu.getTotalDeMilhos() + 1);
            }
            //Atualizar os pruu
            pruu.setMilhos(pombosQueCurtiram);
            pruuRepository.save(pruu);
        } else {
            throw new PomboException("Mensagem não encontrada!");
        }

        return pruuRepository.findById(idPruu);
    }

}
