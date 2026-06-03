package sn.masae.gestion_projets.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "financements")
public class Financement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bailleur;

    @Column(nullable = false)
    private String type; // Don, Prêt, Contrepartie nationale

    @Column(nullable = false)
    private Double montant;

    private Double pourcentage; // calculé automatiquement

    // Lien avec le Projet
    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
}