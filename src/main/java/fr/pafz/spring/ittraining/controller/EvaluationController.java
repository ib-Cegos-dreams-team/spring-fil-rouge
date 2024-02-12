package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.dto.EvaluationReduiteDTO;
import fr.pafz.spring.ittraining.entity.Evaluation;
import fr.pafz.spring.ittraining.service.EvaluationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
@CrossOrigin
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/all")
    public List<EvaluationReduiteDTO> findAll(){
        return evaluationService.findAll();
    }

    @PostMapping("/save")
    public void save(@Validated @RequestBody Evaluation evaluation){
        evaluationService.save(evaluation);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        evaluationService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        evaluationService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Evaluation findById(@PathVariable long id){
        return evaluationService.findById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Evaluation evaluation){
        evaluationService.update(evaluation);
    }
}
