package fr.pafz.spring.ittraining.dto;

import lombok.Data;

@Data
public class AdresseReduiteDTO {

    private Long id;
    private String rue;
    private String ville;
    private String codePostal;
    private String pays;

}
