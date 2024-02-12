package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int note;

    @NotBlank
    @Size(max = 2000)
    private String commentaire;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference("evaluation-utilisateur")
    private Utilisateur utilisateur;

}
