package sn.masae.gestion_projets.model;
import sn.masae.gestion_projets.model.Activite;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data // Lombok génère automatiquement les getters, setters, toString, equals et
      // hashCode
@Entity // Indique que cette classe est une entité JPA
@Table(name = "projets") // Spécifie le nom de la table dans la base de données
public class Projet { // Attributs de l'entité Projet

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String type; // Agriculture ou Elevage

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    private List<ProjetLocalite> localites;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
private List<Activite> activites;

    private LocalDate dateDebutPrevue;
    private LocalDate dateFinPrevue;
    private LocalDate dateFinReelle;

    @Column(nullable = false)
    private String statut; // Planifié, En cours, Terminé, Suspendu

    private Double tauxAvancement;

    private String responsable;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "date_creation")
    private LocalDate dateCreation;
}