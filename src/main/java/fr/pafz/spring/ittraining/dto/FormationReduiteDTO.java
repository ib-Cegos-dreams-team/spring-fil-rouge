package fr.pafz.spring.ittraining.dto;

import lombok.Data;

@Data
public class FormationReduiteDTO {

    private Long id;
    private String nom;
    private String description;
    private float prix;
}
