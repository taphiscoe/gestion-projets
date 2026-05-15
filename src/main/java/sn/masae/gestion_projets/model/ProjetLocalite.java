package sn.masae.gestion_projets.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "projet_localites")
public class ProjetLocalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String departement;

    @Column(nullable = false)
    private String commune;
}