package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.SalleReduiteDTO;
import fr.pafz.spring.ittraining.entity.Salle;
import fr.pafz.spring.ittraining.service.SalleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salles")
@CrossOrigin
public class SalleController {
    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping("/all")
    public List<SalleReduiteDTO> findAll(){
        return salleService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Salle salle){
        salleService.save(salle);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        salleService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        salleService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Salle findById(@PathVariable long id){
        return salleService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Salle salle){
        salleService.update(salle);
    }
}
