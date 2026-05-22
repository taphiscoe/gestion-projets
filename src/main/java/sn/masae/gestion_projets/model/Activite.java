package sn.masae.gestion_projets.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "activites")
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String intitule;

    @Column(columnDefinition = "TEXT")
    private String description;

    

    // Dates prévues → saisies à la création
    private LocalDate dateDebutPrevue;
    private LocalDate dateFinPrevue;

    // Dates réelles → saisies pendant/après
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    // Montants
    private Double montantPrevu;
    private Double montantUtilise;

    // Suivi
    private Double tauxRealisation;
    private String statut; // Planifiée, En cours, Terminée, Annulée

    @Column(columnDefinition = "TEXT")
    private String realisation; // description terrain

    // Dates système
    private LocalDate dateCreation;
    private String creePar;

    // Lien avec le Projet
    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
}