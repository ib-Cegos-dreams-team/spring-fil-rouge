package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
}
