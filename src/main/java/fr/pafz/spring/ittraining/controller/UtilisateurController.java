package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.UtilisateurReduitDTO;
import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.service.UtilisateurService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/all")
    public List<UtilisateurReduitDTO> findAll(){
        return utilisateurService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Utilisateur utilisateur){
        utilisateurService.save(utilisateur);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        utilisateurService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        utilisateurService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Utilisateur findById(@PathVariable long id){
        return utilisateurService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Utilisateur utilisateur){
        utilisateurService.update(utilisateur);
    }
}
