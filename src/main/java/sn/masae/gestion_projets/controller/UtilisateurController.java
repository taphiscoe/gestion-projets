package sn.masae.gestion_projets.controller;

import sn.masae.gestion_projets.model.Utilisateur;
import sn.masae.gestion_projets.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Page de connexion
    @GetMapping("/connexion")
    public String connexion() {
        return "connexion";
    }

    // Liste des utilisateurs (Admin seulement)
    @GetMapping("/admin/utilisateurs")
    public String listeUtilisateurs(Model model) {
        model.addAttribute("utilisateurs", utilisateurRepository.findAll());
        return "admin/utilisateurs/liste";
    }

    // Formulaire nouvel utilisateur
    @GetMapping("/admin/utilisateurs/nouveau")
    public String nouvelUtilisateur(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "admin/utilisateurs/formulaire";
    }

    // Sauvegarder nouvel utilisateur
    @PostMapping("/admin/utilisateurs")
    public String sauvegarderUtilisateur(Utilisateur utilisateur,
                                          RedirectAttributes redirectAttributes) {
        // Encoder le mot de passe avant de sauvegarder
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateur.setStatut("Actif");
        utilisateurRepository.save(utilisateur);
        redirectAttributes.addFlashAttribute("succes", "Utilisateur créé avec succès !");
        return "redirect:/admin/utilisateurs";
    }

    // Bloquer/Débloquer un utilisateur
    @GetMapping("/admin/utilisateurs/{id}/statut")
    public String changerStatut(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow();
        if (utilisateur.getStatut().equals("Actif")) {
            utilisateur.setStatut("Bloqué");
            redirectAttributes.addFlashAttribute("succes", "Utilisateur bloqué !");
        } else {
            utilisateur.setStatut("Actif");
            redirectAttributes.addFlashAttribute("succes", "Utilisateur débloqué !");
        }
        utilisateurRepository.save(utilisateur);
        return "redirect:/admin/utilisateurs";
    }

    // Supprimer un utilisateur
    @GetMapping("/admin/utilisateurs/{id}/supprimer")
    public String supprimerUtilisateur(@PathVariable Long id,
                                        RedirectAttributes redirectAttributes) {
        utilisateurRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("succes", "Utilisateur supprimé !");
        return "redirect:/admin/utilisateurs";
    }
}