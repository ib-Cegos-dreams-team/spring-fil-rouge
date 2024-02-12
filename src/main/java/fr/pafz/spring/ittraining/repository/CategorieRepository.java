package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
}
