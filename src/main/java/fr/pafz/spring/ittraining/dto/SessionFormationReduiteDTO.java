package fr.pafz.spring.ittraining.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SessionFormationReduiteDTO {

    private Long id;
    private Date dateDebut;
    private Date dateFin;
    private int nombreParticipants;
}
