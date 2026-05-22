package sn.masae.gestion_projets.controller;

import sn.masae.gestion_projets.model.Activite;
import sn.masae.gestion_projets.model.Projet;
import sn.masae.gestion_projets.repository.ActiviteRepository;
import sn.masae.gestion_projets.repository.ProjetRepository;
import sn.masae.gestion_projets.service.ActiviteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class ActiviteController {
    @Autowired
private ActiviteService activiteService;
    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private ProjetRepository projetRepository;

    // Liste des activités d'un projet
    @GetMapping("/projets/{projetId}/activites") // URL : /projets/1/activites
    public String listeActivites(@PathVariable Long projetId, Model model) {
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        model.addAttribute("projet", projet); // pour afficher le nom du projet dans la vue et pour les liens vers les
                                              // activités
        model.addAttribute("activites", activiteRepository.findByProjetId(projetId));
        return "activites/liste";
    }

    // Formulaire nouvelle activité
    @GetMapping("/projets/{projetId}/activites/nouveau")
    public String nouvelleActivite(@PathVariable Long projetId, Model model) {
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        model.addAttribute("projet", projet);
        model.addAttribute("activite", new Activite());
        return "activites/formulaire";
    }

    // Sauvegarder nouvelle activité
   
    @PostMapping("/projets/{projetId}/activites")
    public String sauvegarderActivite(@PathVariable Long projetId,
            Activite activite) {
        Projet projet = projetRepository.findById(projetId).orElseThrow();
        activite.setProjet(projet);
        activite.setDateCreation(LocalDate.now());
        activite.setStatut("Planifiée");
        activiteRepository.save(activite);
        return "redirect:/projets/" + projetId + "/activites";
    }

    // Détail d'une activité
    @GetMapping("/projets/{projetId}/activites/{id}")
    public String detailActivite(@PathVariable Long projetId,
            @PathVariable Long id, Model model) {
        Activite activite = activiteRepository.findById(id).orElseThrow();
        model.addAttribute("activite", activite);
        model.addAttribute("projetId", projetId);
        return "activites/detail";
    }

    // Formulaire modification
    @GetMapping("/projets/{projetId}/activites/{id}/modifier")
    public String modifierActivite(@PathVariable Long projetId,
            @PathVariable Long id, Model model) {
        Activite activite = activiteRepository.findById(id).orElseThrow();
        model.addAttribute("activite", activite);
        model.addAttribute("projetId", projetId);
        return "activites/modifier";
    }

    // Sauvegarder modification
    
    @PostMapping("/projets/{projetId}/activites/{id}/modifier")
public String sauvegarderModification(@PathVariable Long projetId,
                                       @PathVariable Long id,
                                       Activite activite) {
    Activite existante = activiteRepository.findById(id).orElseThrow();
    existante.setIntitule(activite.getIntitule());
    existante.setDescription(activite.getDescription());
    existante.setDateDebutPrevue(activite.getDateDebutPrevue());
    existante.setDateFinPrevue(activite.getDateFinPrevue());
    existante.setDateDebutReelle(activite.getDateDebutReelle());
    existante.setDateFinReelle(activite.getDateFinReelle());
    existante.setMontantPrevu(activite.getMontantPrevu());
    existante.setMontantUtilise(activite.getMontantUtilise());
    existante.setStatut(activite.getStatut());
    existante.setRealisation(activite.getRealisation());

    // Calcul automatique du taux !
    activiteService.calculerTauxActivite(existante);

    return "redirect:/projets/" + projetId + "/activites/" + id;
}

    // Supprimer une activité
    @GetMapping("/projets/{projetId}/activites/{id}/supprimer")
    public String supprimerActivite(@PathVariable Long projetId,
            @PathVariable Long id) {
        activiteRepository.deleteById(id);
        return "redirect:/projets/" + projetId + "/activites";
    }
}