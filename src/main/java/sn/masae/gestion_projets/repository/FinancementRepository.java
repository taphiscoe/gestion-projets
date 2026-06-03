package sn.masae.gestion_projets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.masae.gestion_projets.model.Financement;
import java.util.List;

@Repository
public interface FinancementRepository extends JpaRepository<Financement, Long> {

    // Récupérer tous les financements d'un projet
    List<Financement> findByProjetId(Long projetId);
}