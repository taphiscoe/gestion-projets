package sn.masae.gestion_projets.controller;
import sn.masae.gestion_projets.model.Projet; // Import de la classe Projet pour représenter les projets dans le code
import sn.masae.gestion_projets.model.ProjetLocalite;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate; 
import org.springframework.beans.factory.annotation.Autowired; // Import de l'annotation Autowired pour l'injection de dépendances
import org.springframework.stereotype.Controller; // Import de l'annotation Controller pour indiquer que cette classe est un contrôleur Spring MVC 
import org.springframework.ui.Model; // Import de l'interface Model pour passer des données du contrôleur à la vue  
import org.springframework.web.bind.annotation.GetMapping; // Import de l'annotation GetMapping pour gérer les requêtes HTTP GET
import org.springframework.web.bind.annotation.PostMapping;


import sn.masae.gestion_projets.repository.ProjetLocaliteRepository;
import sn.masae.gestion_projets.repository.ProjetRepository; // Import de la classe ProjetRepository pour accéder aux données des projets dans la base de données

@Controller
public class ProjetController { // Annotation pour indiquer que cette classe est un contrôleur Spring MVC

    @Autowired
    private ProjetRepository projetRepository; // Injection de dépendance du ProjetRepository pour accéder aux données des projets
    @Autowired
private ProjetLocaliteRepository projetLocaliteRepository;
    @GetMapping("/projets") // Annotation pour gérer les requêtes HTTP GET à l'URL "/projets"
    public String listeProjets(Model model) { // Méthode pour afficher la liste des projets, le paramètre Model est utilisé pour passer des données à la vue
        model.addAttribute("projets", projetRepository.findAll()); // Récupère tous les projets de la base de données et les ajoute au modèle avec l'attribut "projets"
        return "projets/liste"; // Retourne le nom de la vue à afficher, ici "projets/liste" correspond à un fichier HTML dans le dossier templates/projets
    }
    @GetMapping("/projets/nouveau")// Annotation pour gérer les requêtes HTTP GET à l'URL "/projets/new"
    public String nouveauProjet(Model model) {
        model.addAttribute("projet", new Projet()); // Ajoute un nouvel objet Projet au modèle
        return "projets/formulaire"; // Retourne le nom de la vue à afficher, ici "projets/formulaire" correspond à un fichier HTML dans le dossier templates/projets
    }

    @PostMapping("/projets") // Annotation pour gérer les requêtes HTTP POST à l'URL "/projets"
   
public String sauvegarderProjet(Projet projet,
                                 @RequestParam String region,
                                 @RequestParam String departement,
                                 @RequestParam(required = false) String commune) {
    
    projet.setDateCreation(LocalDate.now());
    projetRepository.save(projet);

    // Créer et sauvegarder la localité
    ProjetLocalite localite = new ProjetLocalite();
    localite.setProjet(projet);
    localite.setRegion(region);
    localite.setDepartement(departement);
    localite.setCommune(commune != null ? commune : "");
    projetLocaliteRepository.save(localite);

    return "redirect:/projets";
}
}