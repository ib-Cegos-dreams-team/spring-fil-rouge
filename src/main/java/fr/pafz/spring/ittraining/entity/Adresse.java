package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codePostal;

    @NotBlank
    private String rue;

    @NotBlank
    private String ville;

    @NotBlank
    private String pays;


    @OneToOne(mappedBy = "adresse" , cascade = CascadeType.MERGE)
    @JsonBackReference("adresse-lieuformation")
    private LieuFormation lieuFormation;


    @OneToOne(mappedBy = "adresse" , cascade = CascadeType.MERGE)
    @JsonBackReference("adresse-utilisateur")
    private Utilisateur utilisateur;

}
