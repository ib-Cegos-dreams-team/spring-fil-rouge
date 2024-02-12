package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.FormationReduiteDTO;
import fr.pafz.spring.ittraining.entity.Formation;
import fr.pafz.spring.ittraining.service.FormationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
@CrossOrigin
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping("/all")
    public List<FormationReduiteDTO> findAll(){
        return formationService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Formation formation){
        formationService.save(formation);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        formationService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        formationService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Formation findById(@PathVariable long id){
        return formationService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Formation formation){
        formationService.update(formation);
    }
}
