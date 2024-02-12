package fr.pafz.spring.ittraining.service;

import fr.pafz.spring.ittraining.entity.Utilisateur;

public interface UtilisateurService {

    public Utilisateur findUserById(Long id) throws Exception;
    public Utilisateur findUserByJwt(String jwt) throws Exception;
}
