package sn.masae.gestion_projets.service;

import sn.masae.gestion_projets.model.Activite;
import sn.masae.gestion_projets.model.Projet;
import sn.masae.gestion_projets.repository.ActiviteRepository;
import sn.masae.gestion_projets.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private ProjetRepository projetRepository;

    // Calcule et met à jour le taux d'une activité
    public void calculerTauxActivite(Activite activite) {
        if (activite.getMontantPrevu() != null 
            && activite.getMontantPrevu() > 0 
            && activite.getMontantUtilise() != null) {
            
            double taux = (activite.getMontantUtilise() / activite.getMontantPrevu()) * 100;
            // On plafonne à 100%
            taux = Math.min(taux, 100.0);
            // On arrondit à 1 décimale
            taux = Math.round(taux * 10.0) / 10.0;
            activite.setTauxRealisation(taux);
        } else {
            activite.setTauxRealisation(0.0);
        }
        activiteRepository.save(activite);
        
        // On recalcule aussi le taux du projet
        calculerTauxProjet(activite.getProjet());
    }

    // Calcule et met à jour le taux global du projet
    public void calculerTauxProjet(Projet projet) {
        List<Activite> activites = activiteRepository.findByProjetId(projet.getId());
        
        if (activites.isEmpty()) {
            projet.setTauxAvancement(0.0);
        } else {
            double somme = activites.stream()
                .mapToDouble(a -> a.getTauxRealisation() != null ? a.getTauxRealisation() : 0.0)
                .sum();
            double moyenne = somme / activites.size();
            moyenne = Math.round(moyenne * 10.0) / 10.0;
            projet.setTauxAvancement(moyenne);
        }
        projetRepository.save(projet);
    }
}