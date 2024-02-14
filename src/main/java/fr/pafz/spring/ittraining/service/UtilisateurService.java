package fr.pafz.spring.ittraining.service;

import fr.pafz.spring.ittraining.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurService{

    public Utilisateur findUserById(Long id) throws Exception;
    public Utilisateur findUserByJwt(String jwt) throws Exception;

    public Utilisateur deleteUserById(Long id) throws Exception;
}
