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
    @Size(min=2,max=20)
    private String nom;


    @Size(min=10,max=1000)
    private String description;


    @OneToMany(mappedBy = "categorie", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference("theme-categorie")
    private List<Theme> themes;


    @OneToMany(mappedBy = "categorie", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference("categorie-formation")
    private List<Formation> formations;

}
