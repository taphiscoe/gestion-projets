package sn.masae.gestion_projets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.masae.gestion_projets.model.ProjetLocalite;

@Repository
public interface ProjetLocaliteRepository extends JpaRepository<ProjetLocalite, Long> {

}