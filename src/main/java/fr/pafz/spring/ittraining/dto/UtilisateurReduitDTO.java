package fr.pafz.spring.ittraining.dto;

import fr.pafz.spring.ittraining.Enum.Civilite;
import fr.pafz.spring.ittraining.Enum.Role;
import lombok.Data;

@Data
public class UtilisateurReduitDTO {

    private Long id;
    private Civilite civilite;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private Role role;
}
