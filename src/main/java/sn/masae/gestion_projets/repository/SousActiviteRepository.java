package sn.masae.gestion_projets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.masae.gestion_projets.model.SousActivite;
import java.util.List;

@Repository
public interface SousActiviteRepository extends JpaRepository<SousActivite, Long> {

    // Récupérer toutes les sous-activités d'une activité
    List<SousActivite> findByActiviteId(Long activiteId);
}