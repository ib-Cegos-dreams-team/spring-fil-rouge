package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.FormationReduiteDTO;
import fr.pafz.spring.ittraining.entity.Formation;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.FormationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService {
    private final FormationRepository formationRepository;
    private final JdbcTemplate jdbcTemplate;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public FormationService(FormationRepository formationRepository, JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.formationRepository = formationRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des formations de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'informations et que les données restent lisible.
     * @return la liste des formations enregistrées dans la base de donnée.
     */
    public List<FormationReduiteDTO> findAll(){
        List<Formation> formations = formationRepository.findAll();
        // cette methode permet de mapper le format formation complete au format formation reduite.
        return formations.stream().map(formation -> objectMapper
                .convertValue(formation, FormationReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne la formation en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return la formation complete, ou un message d'erreur si pas trouvé
     */
    public Formation findById(Long id){
        return formationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Formation with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une formation et de l'enregistrement de cette dernière dans la base de données
     * @param formation
     * @return l'formation créée
     */
    public Formation save(Formation formation){
        return formationRepository.save(formation);
    }

    /**
     * Methode de mise à jour d'une formation dans la base de données
     * @param formation
     * @return la formation modifiée
     */
    public void update(Formation formation){
        String updateQuery = "UPDATE Formation SET description = ?, isInterEntreprise = ?,, isIntraEntreprise = ?," +
                " nom = ?, prix=? , categorie_id=?, sous_theme_id = ? , theme_id = ? WHERE id = ?";
        jdbcTemplate.update(updateQuery,
                formation.getDescription(),
                formation.isInterEntreprise(),
                formation.isIntraEntreprise(),
                formation.getNom(),
                formation.getPrix(),
                formation.getCategorie().getId(),
                formation.getSousTheme().getId(),
                formation.getTheme().getId(),
                formation.getId());
    }

    /**
     * Methode pour effacer l'ensemble des formations dans la base de données
     */
    public void deleteAll(){
        formationRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'formation en fonction de l'id passé en paramètre.
     * @param id
     * @return la formation qui a été supprimée.
     */
    public Formation deleteById(Long id){
        Formation formation = findById(id);
        formationRepository.deleteById(id);
        return formation;
    }

}
