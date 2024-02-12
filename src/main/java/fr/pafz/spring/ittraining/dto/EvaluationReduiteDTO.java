package fr.pafz.spring.ittraining.dto;

import lombok.Data;

@Data
public class EvaluationReduiteDTO {

    private Long id;
    private int note;
    private String commentaire;
}
