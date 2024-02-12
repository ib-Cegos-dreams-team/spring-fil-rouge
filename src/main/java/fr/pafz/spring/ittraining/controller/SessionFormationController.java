package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.SessionFormationReduiteDTO;
import fr.pafz.spring.ittraining.entity.SessionFormation;
import fr.pafz.spring.ittraining.service.SessionFormationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessionformations")
@CrossOrigin
public class SessionFormationController {
    private final SessionFormationService sessionFormationService;

    public SessionFormationController(SessionFormationService sessionFormationService) {
        this.sessionFormationService = sessionFormationService;
    }

    @GetMapping("/all")
    public List<SessionFormationReduiteDTO> findAll(){
        return sessionFormationService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody SessionFormation sessionFormation){
        sessionFormationService.save(sessionFormation);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        sessionFormationService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        sessionFormationService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public SessionFormation findById(@PathVariable long id){
        return sessionFormationService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody SessionFormation sessionFormation){
        sessionFormationService.update(sessionFormation);
    }
}
