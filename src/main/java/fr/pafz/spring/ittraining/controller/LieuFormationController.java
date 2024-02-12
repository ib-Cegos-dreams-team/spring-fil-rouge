package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.LieuFormationReduiteDTO;
import fr.pafz.spring.ittraining.entity.LieuFormation;
import fr.pafz.spring.ittraining.service.LieuFormationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lieuformations")
@CrossOrigin
public class LieuFormationController {

    private final LieuFormationService lieuFormationService;

    public LieuFormationController(LieuFormationService lieuFormationService) {
        this.lieuFormationService = lieuFormationService;
    }

    @GetMapping("/all")
    public List<LieuFormationReduiteDTO> findAll(){
        return lieuFormationService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody LieuFormation lieuFormation){
        lieuFormationService.save(lieuFormation);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        lieuFormationService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        lieuFormationService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public LieuFormation findById(@PathVariable long id){
        return lieuFormationService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody LieuFormation lieuFormation){
        lieuFormationService.update(lieuFormation);
    }
}
