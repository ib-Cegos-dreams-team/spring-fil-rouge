package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;


    @Size(min=10,max=1000)
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonBackReference("theme-categorie")
    private Categorie categorie;


    @OneToMany(mappedBy = "theme",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference("theme-formation")
    private List<Formation> formations;


    @OneToMany(mappedBy = "theme",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference("theme-soustheme")
    private List<SousTheme> sousThemes;

}
