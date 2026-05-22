package sn.masae.gestion_projets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.masae.gestion_projets.model.Activite;
import java.util.List;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite, Long> {

    // Récupérer toutes les activités d'un projet
    List<Activite> findByProjetId(Long projetId);
}