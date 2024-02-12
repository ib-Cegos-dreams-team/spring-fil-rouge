package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation,Long> {
}
