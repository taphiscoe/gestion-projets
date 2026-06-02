package sn.masae.gestion_projets.controller;

import sn.masae.gestion_projets.model.Activite;
import sn.masae.gestion_projets.model.SousActivite;
import sn.masae.gestion_projets.repository.ActiviteRepository;
import sn.masae.gestion_projets.repository.SousActiviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class SousActiviteController {

    @Autowired
    private SousActiviteRepository sousActiviteRepository;

    @Autowired
    private ActiviteRepository activiteRepository;

    // Liste des sous-activités d'une activité
    @GetMapping("/projets/{projetId}/activites/{activiteId}/sousactivites")
    public String listeSousActivites(@PathVariable Long projetId,
                                      @PathVariable Long activiteId, Model model) {
        Activite activite = activiteRepository.findById(activiteId).orElseThrow();
        model.addAttribute("activite", activite);
        model.addAttribute("projetId", projetId);
        model.addAttribute("sousActivites", sousActiviteRepository.findByActiviteId(activiteId));
        return "sousactivites/liste";
    }

    // Formulaire nouvelle sous-activité
    @GetMapping("/projets/{projetId}/activites/{activiteId}/sousactivites/nouveau")
    public String nouvelleSousActivite(@PathVariable Long projetId,
                                        @PathVariable Long activiteId, Model model) {
        Activite activite = activiteRepository.findById(activiteId).orElseThrow();
        model.addAttribute("activite", activite);
        model.addAttribute("projetId", projetId);
        model.addAttribute("sousActivite", new SousActivite());
        return "sousactivites/formulaire";
    }

    // Sauvegarder nouvelle sous-activité
    @PostMapping("/projets/{projetId}/activites/{activiteId}/sousactivites")
    public String sauvegarderSousActivite(@PathVariable Long projetId,
                                           @PathVariable Long activiteId,
                                           SousActivite sousActivite) {
        Activite activite = activiteRepository.findById(activiteId).orElseThrow();
        sousActivite.setActivite(activite);
        sousActivite.setDateCreation(LocalDate.now());
        sousActivite.setStatut("Planifiée");
        sousActiviteRepository.save(sousActivite);
        return "redirect:/projets/" + projetId + "/activites/" + activiteId + "/sousactivites";
    }

    // Détail d'une sous-activité
    @GetMapping("/projets/{projetId}/activites/{activiteId}/sousactivites/{id}")
    public String detailSousActivite(@PathVariable Long projetId,
                                      @PathVariable Long activiteId,
                                      @PathVariable Long id, Model model) {
        SousActivite sousActivite = sousActiviteRepository.findById(id).orElseThrow();
        model.addAttribute("sousActivite", sousActivite);
        model.addAttribute("activiteId", activiteId);
        model.addAttribute("projetId", projetId);
        return "sousactivites/detail";
    }

    // Formulaire modification
    @GetMapping("/projets/{projetId}/activites/{activiteId}/sousactivites/{id}/modifier")
    public String modifierSousActivite(@PathVariable Long projetId,
                                        @PathVariable Long activiteId,
                                        @PathVariable Long id, Model model) {
        SousActivite sousActivite = sousActiviteRepository.findById(id).orElseThrow();
        model.addAttribute("sousActivite", sousActivite);
        model.addAttribute("activiteId", activiteId);
        model.addAttribute("projetId", projetId);
        return "sousactivites/modifier";
    }

    // Sauvegarder modification
    @PostMapping("/projets/{projetId}/activites/{activiteId}/sousactivites/{id}/modifier")
    public String sauvegarderModification(@PathVariable Long projetId,
                                           @PathVariable Long activiteId,
                                           @PathVariable Long id,
                                           SousActivite sousActivite) {
        SousActivite existante = sousActiviteRepository.findById(id).orElseThrow();
        existante.setIntitule(sousActivite.getIntitule());
        existante.setDescription(sousActivite.getDescription());
        existante.setDateDebutPrevue(sousActivite.getDateDebutPrevue());
        existante.setDateFinPrevue(sousActivite.getDateFinPrevue());
        existante.setDateDebutReelle(sousActivite.getDateDebutReelle());
        existante.setDateFinReelle(sousActivite.getDateFinReelle());
        existante.setMontantPrevu(sousActivite.getMontantPrevu());
        existante.setMontantUtilise(sousActivite.getMontantUtilise());
        existante.setTauxRealisation(sousActivite.getTauxRealisation());
        existante.setStatut(sousActivite.getStatut());
        existante.setRealisation(sousActivite.getRealisation());
        sousActiviteRepository.save(existante);
        return "redirect:/projets/" + projetId + "/activites/" + activiteId + "/sousactivites/" + id;
    }

    // Supprimer une sous-activité
    @GetMapping("/projets/{projetId}/activites/{activiteId}/sousactivites/{id}/supprimer")
    public String supprimerSousActivite(@PathVariable Long projetId,
                                         @PathVariable Long activiteId,
                                         @PathVariable Long id) {
        sousActiviteRepository.deleteById(id);
        return "redirect:/projets/" + projetId + "/activites/" + activiteId + "/sousactivites";
    }
}