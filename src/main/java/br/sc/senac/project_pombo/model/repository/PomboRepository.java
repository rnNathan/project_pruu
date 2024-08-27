package br.sc.senac.project_pombo.model.repository;

import br.sc.senac.project_pombo.model.entity.Pombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PomboRepository extends JpaRepository<Pombo, String> {
}
