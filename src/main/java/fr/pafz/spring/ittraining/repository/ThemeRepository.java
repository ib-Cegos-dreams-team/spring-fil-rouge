package fr.pafz.spring.ittraining.repository;

import fr.pafz.spring.ittraining.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme,Long> {
}
