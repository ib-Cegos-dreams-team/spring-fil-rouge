package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SousTheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    @Size(min=10,max=1000)
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonBackReference("theme-soustheme")
    private Theme theme;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonBackReference("soustheme-formation")
    private Formation formation;
}
