package br.sc.senac.project_pombo.model.repository;


import br.sc.senac.project_pombo.model.entity.Denuncia;
import br.sc.senac.project_pombo.model.entity.Pombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, String>, JpaSpecificationExecutor<Denuncia> {
}
