package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.ThemeReduitDTO;
import fr.pafz.spring.ittraining.entity.Categorie;
import fr.pafz.spring.ittraining.entity.Theme;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.service.CategorieService;
import fr.pafz.spring.ittraining.service.ThemeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
@CrossOrigin
public class ThemeController {

    private final ThemeService themeService;
    private final CategorieService categorieService;

    public ThemeController(ThemeService themeService, CategorieService categorieService) {
        this.themeService = themeService;
        this.categorieService = categorieService;
    }

    @GetMapping("/all")
    public List<ThemeReduitDTO> findAll(){
        return themeService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Theme theme){
        Categorie categorie = categorieService.findById(theme.getCategorie().getId());
        if (categorie == null){
            throw new NotFoundException("Categorie not found");
        }
        theme.setCategorie(categorie);

        themeService.save(theme);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        themeService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        themeService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Theme findById(@PathVariable long id){
        return themeService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Theme theme){
        themeService.update(theme);
    }


    @GetMapping("/findbycategorie/{id}")
    public List<ThemeReduitDTO> findByCategorieId(@PathVariable long id){
        return themeService.findByIdCategorie(id);
    }

    @PostMapping("/savelist")
    public void saveList(@RequestBody List<Theme> themes){themeService.saveListThemes(themes);}


}
