package sn.masae.gestion_projets.controller;

import sn.masae.gestion_projets.model.Projet; // Import de la classe Projet pour représenter les projets dans le code
import sn.masae.gestion_projets.model.ProjetLocalite;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Import de l'annotation Autowired pour l'injection de dépendances
import org.springframework.stereotype.Controller; // Import de l'annotation Controller pour indiquer que cette classe est un contrôleur Spring MVC 
import org.springframework.ui.Model; // Import de l'interface Model pour passer des données du contrôleur à la vue  
import org.springframework.web.bind.annotation.GetMapping; // Import de l'annotation GetMapping pour gérer les requêtes HTTP GET
import org.springframework.web.bind.annotation.PathVariable; // Import de l'annotation PathVariable pour extraire des variables de l'URL
import org.springframework.web.bind.annotation.PostMapping;

import sn.masae.gestion_projets.repository.ProjetLocaliteRepository;
import sn.masae.gestion_projets.repository.ProjetRepository; // Import de la classe ProjetRepository pour accéder aux données des projets dans la base de données

@Controller
public class ProjetController { // Annotation pour indiquer que cette classe est un contrôleur Spring MVC

    @Autowired
    private ProjetRepository projetRepository; // Injection de dépendance du ProjetRepository pour accéder aux données
                                               // des projets
    @Autowired
    private ProjetLocaliteRepository projetLocaliteRepository;

    @GetMapping("/projets") // Annotation pour gérer les requêtes HTTP GET à l'URL "/projets"
    public String listeProjets(Model model) { // Méthode pour afficher la liste des projets, le paramètre Model est
                                              // utilisé pour passer des données à la vue
        model.addAttribute("projets", projetRepository.findAll()); // Récupère tous les projets de la base de données et
                                                                   // les ajoute au modèle avec l'attribut "projets"
        return "projets/liste"; // Retourne le nom de la vue à afficher, ici "projets/liste" correspond à un
                                // fichier HTML dans le dossier templates/projets
    }

    @GetMapping("/projets/nouveau") // Annotation pour gérer les requêtes HTTP GET à l'URL "/projets/new"
    public String nouveauProjet(Model model) {
        model.addAttribute("projet", new Projet()); // Ajoute un nouvel objet Projet au modèle
        return "projets/formulaire"; // Retourne le nom de la vue à afficher, ici "projets/formulaire" correspond à
                                     // un fichier HTML dans le dossier templates/projets
    }

    @PostMapping("/projets") // Annotation pour gérer les requêtes HTTP POST à l'URL "/projets"

    public String sauvegarderProjet(Projet projet,
            @RequestParam List<String> regions,
            @RequestParam List<String> departements,
            @RequestParam(required = false) List<String> communes) {

        projet.setDateCreation(LocalDate.now());
        projetRepository.save(projet);

        // Sauvegarder chaque localité
        for (int i = 0; i < regions.size(); i++) {
            ProjetLocalite localite = new ProjetLocalite();
            localite.setProjet(projet);
            localite.setRegion(regions.get(i));
            localite.setDepartement(departements.get(i));
            localite.setCommune(communes != null && i < communes.size() ? communes.get(i) : "");
            projetLocaliteRepository.save(localite);
        }

        return "redirect:/projets";
    }

    // Afficher le détail d'un projet
    @GetMapping("/projets/{id}")
    public String detailProjet(@PathVariable Long id, Model model) {
        Projet projet = projetRepository.findById(id).orElseThrow();
        model.addAttribute("projet", projet);
        return "projets/detail";
    }

    // Afficher le formulaire de modification
    @GetMapping("/projets/{id}/modifier")
    public String modifierProjet(@PathVariable Long id, Model model) {
        Projet projet = projetRepository.findById(id).orElseThrow();
        model.addAttribute("projet", projet);
        return "projets/modifier";
    }

    // Sauvegarder la modification
    @PostMapping("/projets/{id}/modifier")
    public String sauvegarderModification(@PathVariable Long id, Projet projet) {
        Projet existant = projetRepository.findById(id).orElseThrow();
        existant.setNom(projet.getNom());
        existant.setCode(projet.getCode());
        existant.setDescription(projet.getDescription());
        existant.setType(projet.getType());
        existant.setStatut(projet.getStatut());
        existant.setDateDebutPrevue(projet.getDateDebutPrevue());
        existant.setDateFinPrevue(projet.getDateFinPrevue());
        existant.setResponsable(projet.getResponsable());
        projetRepository.save(existant);
        return "redirect:/projets/" + id;
    }

    // Supprimer un projet
    @GetMapping("/projets/{id}/supprimer")
    public String supprimerProjet(@PathVariable Long id) {
        projetRepository.deleteById(id);
        return "redirect:/projets";
    }
}