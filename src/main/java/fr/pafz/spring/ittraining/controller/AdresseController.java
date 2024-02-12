package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.AdresseReduiteDTO;
import fr.pafz.spring.ittraining.entity.Adresse;
import fr.pafz.spring.ittraining.service.AdresseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
@CrossOrigin
public class AdresseController {

    private final AdresseService adresseService;

    public AdresseController(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @GetMapping("/all")
    public List<AdresseReduiteDTO> findAll(){
        return adresseService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Adresse adresse){
        adresseService.save(adresse);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        adresseService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        adresseService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Adresse findById(@PathVariable long id){
        return adresseService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Adresse adresse){
        adresseService.update(adresse);
    }
}
