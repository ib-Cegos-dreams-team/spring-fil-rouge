package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.SousThemeReduitDTO;
import fr.pafz.spring.ittraining.entity.SousTheme;
import fr.pafz.spring.ittraining.service.SousThemeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sousthemes")
@CrossOrigin
public class SousThemeController {

    private final SousThemeService sousThemeService;

    public SousThemeController(SousThemeService sousThemeService) {
        this.sousThemeService = sousThemeService;
    }

    @GetMapping("/all")
    public List<SousThemeReduitDTO> findAll(){
        return sousThemeService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody SousTheme sousTheme){
        sousThemeService.save(sousTheme);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        sousThemeService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        sousThemeService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public SousTheme findById(@PathVariable long id){
        return sousThemeService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody SousTheme sousTheme){
        sousThemeService.update(sousTheme);
    }
}
