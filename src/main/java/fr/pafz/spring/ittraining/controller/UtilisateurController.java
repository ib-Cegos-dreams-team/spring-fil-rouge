package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtilisateurController {
    public UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/api/utilisateurs/profile")
    public Utilisateur findUserByJwt(@RequestHeader(name = "Authorization") String jwt) throws Exception {
        return utilisateurService.findUserByJwt(jwt);
    }
}
