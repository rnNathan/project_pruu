package br.sc.senac.project_pombo.model.seletor;

import br.sc.senac.project_pombo.model.entity.Pruu;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.sc.senac.project_pombo.model.seletor.BaseSeletor.filtroPerido;

@Data
public class PruuSeletor extends BaseSeletor implements Specification<Pruu> {

    private String mensagem;

    //Period filter
    private LocalDate dataInicioPostagem;
    private LocalDate dataFimPostagem;

    public boolean temFiltro(){
        return (this.mensagem != null && this.mensagem.trim().length() > 0)
                || (this.dataInicioPostagem != null)
                || (this.dataFimPostagem != null);

    }
    @Override
    public Predicate toPredicate(Root<Pruu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();

            if(this.getMensagem() != null && this.getMensagem().trim().length() > 0) {
                predicates.add(cb.like(root.get("mensagem"), "%" + this.getMensagem() + "%"));

            }

            filtroPerido(root,cb, predicates,this.dataInicioPostagem, this.dataFimPostagem, "dataCriada");

            return cb.and(predicates.toArray(new Predicate[0]));
    }
}
