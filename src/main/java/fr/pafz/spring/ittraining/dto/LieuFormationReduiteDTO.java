package fr.pafz.spring.ittraining.dto;

import fr.pafz.spring.ittraining.entity.Adresse;
import lombok.Data;

@Data
public class LieuFormationReduiteDTO {
    private Long id;
    private String email;
    private String telephone;
    private String ville;
    private Adresse adresse;
}
