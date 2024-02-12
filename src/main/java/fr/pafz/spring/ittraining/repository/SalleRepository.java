package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle,Long> {
}
