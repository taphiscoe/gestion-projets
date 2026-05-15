package sn.masae.gestion_projets.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Import de JpaRepository pour les opérations CRUD
import org.springframework.stereotype.Repository; // Import de l'annotation Repository pour indiquer que cette interface est un composant de persistance
import sn.masae.gestion_projets.model.Projet; // Import de la classe Projet qui représente l'entité que nous allons gérer

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

}