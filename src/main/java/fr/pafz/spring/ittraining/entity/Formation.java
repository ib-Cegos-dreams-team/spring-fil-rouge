package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String description;

    private boolean isIntraEntreprise;

    private boolean isInterEntreprise;

    private float prix;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference("soustheme-formation")
    private SousTheme sousTheme;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference("theme-formation")
    private Theme theme;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference("categorie-formation")
    private Categorie categorie;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "formation_session",
            joinColumns = @JoinColumn(name = "formation_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    private List<SessionFormation> sessionFormations;

}
