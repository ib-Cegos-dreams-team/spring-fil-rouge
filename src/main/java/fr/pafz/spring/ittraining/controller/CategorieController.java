package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.CategorieReduiteDTO;
import fr.pafz.spring.ittraining.entity.Categorie;
import fr.pafz.spring.ittraining.service.CategorieService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/all")
    public List<CategorieReduiteDTO> findAll(){
        return categorieService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Categorie categorie){
        categorieService.save(categorie);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        categorieService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        categorieService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Categorie findById(@PathVariable long id){
        return categorieService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Categorie categorie){
        categorieService.update(categorie);
    }
    
}
