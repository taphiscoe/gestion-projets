package sn.masae.gestion_projets.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sous_activites")
public class SousActivite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String intitule;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate dateDebutPrevue;
    private LocalDate dateFinPrevue;

    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    private Double montantPrevu;
    private Double montantUtilise;

    private Double tauxRealisation;
    private String statut;

    @Column(columnDefinition = "TEXT")
    private String realisation;

    private LocalDate dateCreation;
    private String creePar;

    // Lien avec l'Activité
    @ManyToOne
    @JoinColumn(name = "activite_id", nullable = false)
    private Activite activite;
}