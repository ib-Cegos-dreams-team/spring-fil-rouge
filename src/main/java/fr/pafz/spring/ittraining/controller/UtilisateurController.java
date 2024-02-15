package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.service.UtilisateurService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtilisateurController {
    public UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Méthode qui permet de récupérer un utilisateur par son token
     * @param jwt token
     * @return Utilisateur
     * @throws Exception si l'utilisateur n'est pas retrouvé
     */
    @GetMapping("/api/utilisateurs/profile")
    public Utilisateur findUserByJwt(@RequestHeader(name = "Authorization") String jwt) throws Exception {
        return utilisateurService.findUserByJwt(jwt);
    }

    /**
     * Méthode qui permet de récupérer un utilisateur par son id
     *
     */
    @GetMapping("/api/utilisateurs/{id}")
    public Utilisateur findUserById(@PathVariable Long id) throws Exception {
        return utilisateurService.findUserById(id);
    }

    /**
     * Méthode qui permet de supprimer un utilisateur par son id
     * @param id id de l'utilisateur
     * @return Utilisateur
     * @throws Exception si l'utilisateur n'est pas retrouvé
     */
    @DeleteMapping("/api/utilisateurs/{id}")
    public Utilisateur deleteUserById(@PathVariable Long id) throws Exception {
        return utilisateurService.deleteUserById(id);
    }

}
