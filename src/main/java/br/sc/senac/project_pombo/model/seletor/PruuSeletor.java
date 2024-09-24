package br.sc.senac.project_pombo.model.seletor;

import br.sc.senac.project_pombo.model.entity.Pruu;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PruuSeletor extends BaseSeletor implements Specification<Pruu> {

    private String Pruu;

    private String idPombo;

    //Period filter
    private LocalDate dataInicioPostagem;
    private LocalDate dataFimPostagem;

    private Integer quantidadeMinima;
    private Integer quantidadeMaxima;

    public boolean temFiltro() {
        return (this.Pruu != null && this.Pruu.trim().length() > 0)
                || (this.idPombo != null && this.idPombo.trim().length() > 0)
                || (this.dataInicioPostagem != null)
                || (this.dataFimPostagem != null)
                || (this.quantidadeMinima != null)
                || (this.quantidadeMaxima != null);

    }

    @Override
    public Predicate toPredicate(Root<Pruu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (this.getPruu() != null && this.getPruu().trim().length() > 0) {
            predicates.add(cb.like(root.get("Pruu"), "%" + this.getPruu() + "%"));

        }

        if (this.getIdPombo() != null) {
            predicates.add(cb.like(root.get("idPombo"), "%" + this.getIdPombo() + "%"));
        }
        filtroPeriodo(root, cb, predicates, this.dataInicioPostagem, this.dataFimPostagem, "dataCriada");
        filtroPorMilhos(root, cb, predicates, this.quantidadeMinima, this.quantidadeMaxima, "totalDeMilhos");
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
