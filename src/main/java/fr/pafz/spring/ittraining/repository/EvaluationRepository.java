package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {
}
