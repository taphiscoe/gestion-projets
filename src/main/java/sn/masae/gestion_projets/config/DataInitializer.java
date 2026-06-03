package sn.masae.gestion_projets.config;

import sn.masae.gestion_projets.model.Utilisateur;
import sn.masae.gestion_projets.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Créer l'admin par défaut s'il n'existe pas
        if (utilisateurRepository.findByEmail("admin@masae.sn").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("MASAE");
            admin.setPrenom("Administrateur");
            admin.setEmail("admin@masae.sn");
            admin.setMotDePasse(passwordEncoder.encode("masae2025"));
            admin.setRole("ADMIN");
            admin.setStatut("Actif");
            utilisateurRepository.save(admin);
            System.out.println("✅ Compte admin créé : admin@masae.sn / masae2025");
        }
    }
}