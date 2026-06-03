package sn.masae.gestion_projets.controller;

import sn.masae.gestion_projets.model.Financement;
import sn.masae.gestion_projets.model.Projet;
import sn.masae.gestion_projets.repository.FinancementRepository;
import sn.masae.gestion_projets.repository.ProjetRepository;
import sn.masae.gestion_projets.service.FinancementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FinancementController {

    @Autowired
    private FinancementRepository financementRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private FinancementService financementService;

    // Liste des financements d'un projet
    @GetMapping("/projets/{projetId}/financements")
    public String listeFinancements(@PathVariable Long projetId, Model model) {
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        model.addAttribute("projet", projet);
        model.addAttribute("financements", financementRepository.findByProjetId(projetId));
        
        // Budget total
        double budgetTotal = financementRepository.findByProjetId(projetId)
                .stream().mapToDouble(Financement::getMontant).sum();
        model.addAttribute("budgetTotal", budgetTotal);
        
        return "financements/liste";
    }

    // Formulaire nouveau financement
    @GetMapping("/projets/{projetId}/financements/nouveau")
    public String nouveauFinancement(@PathVariable Long projetId, Model model) {
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        model.addAttribute("projet", projet);
        model.addAttribute("financement", new Financement());
        return "financements/formulaire";
    }

    // Sauvegarder nouveau financement
    @PostMapping("/projets/{projetId}/financements")
    public String sauvegarderFinancement(@PathVariable Long projetId,
                                          Financement financement,
                                          RedirectAttributes redirectAttributes) {
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        financement.setProjet(projet);
        financementRepository.save(financement);
        
        // Recalcul des pourcentages
        financementService.recalculerPourcentages(projet);
        
        redirectAttributes.addFlashAttribute("succes", "Financement ajouté avec succès !");
        return "redirect:/projets/" + projetId + "/financements";
    }

    // Supprimer un financement
    @GetMapping("/projets/{projetId}/financements/{id}/supprimer")
    public String supprimerFinancement(@PathVariable Long projetId,
                                        @PathVariable Long id,
                                        RedirectAttributes redirectAttributes) {
        financementRepository.deleteById(id);
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        financementService.recalculerPourcentages(projet);
        redirectAttributes.addFlashAttribute("succes", "Financement supprimé !");
        return "redirect:/projets/" + projetId + "/financements";
    }
}