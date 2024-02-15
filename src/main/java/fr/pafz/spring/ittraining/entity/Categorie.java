package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;



    private String description;


    @OneToMany(mappedBy = "categorie", cascade = {CascadeType.MERGE})
    @JsonManagedReference("theme-categorie")
    private List<Theme> themes;


    @OneToMany(mappedBy = "categorie", cascade = {CascadeType.MERGE})
    @JsonManagedReference("categorie-formation")
    private List<Formation> formations;

}
