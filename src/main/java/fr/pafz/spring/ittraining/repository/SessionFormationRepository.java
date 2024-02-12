package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFormationRepository extends JpaRepository<SessionFormation,Long> {
}
