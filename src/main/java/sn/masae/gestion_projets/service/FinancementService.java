package sn.masae.gestion_projets.service;

import sn.masae.gestion_projets.model.Financement;
import sn.masae.gestion_projets.model.Projet;
import sn.masae.gestion_projets.repository.FinancementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FinancementService {

    @Autowired
    private FinancementRepository financementRepository;

    // Calcule le budget total et les pourcentages
    public void recalculerPourcentages(Projet projet) {
        List<Financement> financements = financementRepository.findByProjetId(projet.getId());

        // Calcul du budget total
        double budgetTotal = financements.stream()
                .mapToDouble(Financement::getMontant)
                .sum();

        // Calcul du pourcentage de chaque financement
        for (Financement f : financements) {
            if (budgetTotal > 0) {
                double pourcentage = (f.getMontant() / budgetTotal) * 100;
                pourcentage = Math.round(pourcentage * 10.0) / 10.0;
                f.setPourcentage(pourcentage);
            } else {
                f.setPourcentage(0.0);
            }
            financementRepository.save(f);
        }
    }

    // Vérifie si le montant d'une activité dépasse le budget total
    public boolean verifierBudgetActivites(Projet projet, Double montantNouvelleActivite) {
        List<Financement> financements = financementRepository.findByProjetId(projet.getId());
        
        double budgetTotal = financements.stream()
                .mapToDouble(Financement::getMontant)
                .sum();

        double totalActivites = projet.getActivites().stream()
                .mapToDouble(a -> a.getMontantPrevu() != null ? a.getMontantPrevu() : 0.0)
                .sum();

        return (totalActivites + montantNouvelleActivite) <= budgetTotal;
    }

    // Vérifie si le montant d'une sous-activité dépasse le budget de l'activité
public boolean verifierBudgetSousActivites(sn.masae.gestion_projets.model.Activite activite, Double montantNouvelleSousActivite) {
    
    double budgetActivite = activite.getMontantPrevu() != null ? activite.getMontantPrevu() : 0.0;

    double totalSousActivites = activite.getSousActivites().stream()
            .mapToDouble(sa -> sa.getMontantPrevu() != null ? sa.getMontantPrevu() : 0.0)
            .sum();

    return (totalSousActivites + montantNouvelleSousActivite) <= budgetActivite;
}
}