package br.sc.senac.project_pombo.model.seletor;

import br.sc.senac.project_pombo.model.entity.Pruu;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public abstract class BaseSeletor {

    private int pagina;
    private int limite;

    public BaseSeletor() {
        this.limite = 0;
        this.pagina = 0;
    }

    public boolean temPaginacao() {
        return this.limite > 0 && this.pagina > 0;
    }

    static void filtroPerido(Root<Pruu> root, CriteriaBuilder cb, List<Predicate> predicates, LocalDate dataInicioPostagem, LocalDate dataFimPostagem, String nomeAtributo) {
        if (dataInicioPostagem != null && dataFimPostagem != null) {
            predicates.add(cb.between(root.get(nomeAtributo), dataInicioPostagem, dataFimPostagem));
        } else if (dataInicioPostagem != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(nomeAtributo), dataInicioPostagem));
        } else if (dataFimPostagem != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(nomeAtributo), dataFimPostagem));
        }
    }

}
