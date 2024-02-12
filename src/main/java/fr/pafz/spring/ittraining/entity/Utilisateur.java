package fr.pafz.spring.ittraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.pafz.spring.ittraining.Enum.Civilite;
import fr.pafz.spring.ittraining.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    private String telephone;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    private String password;

//    public void encodePassword() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        this.password = passwordEncoder.encode(this.password);
//    }

    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.MERGE)
    @JsonManagedReference("evaluation-utilisateur")
    private List<Evaluation> evaluations;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference("session-utilisateur")
    private SessionFormation session;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonManagedReference("adresse-utilisateur")
    private Adresse adresse;

}
