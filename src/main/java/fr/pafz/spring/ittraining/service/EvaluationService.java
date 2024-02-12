package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.EvaluationReduiteDTO;
import fr.pafz.spring.ittraining.entity.Evaluation;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.EvaluationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    private final JdbcTemplate jdbcTemplate;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public EvaluationService(EvaluationRepository evaluationRepository, JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.evaluationRepository = evaluationRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des evaluations de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'informations et que les données restent lisible.
     * @return la liste des evaluations enregistrées dans la base de donnée.
     */
    public List<EvaluationReduiteDTO> findAll(){
        List<Evaluation> evaluations = evaluationRepository.findAll();
        // cette methode permet de mapper le format evaluation complete au format evaluation reduite.
        return evaluations.stream().map(evaluation -> objectMapper
                .convertValue(evaluation, EvaluationReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne l'evaluation en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return l'evaluation complete, ou un message d'erreur si pas trouvé
     */
    public Evaluation findById(Long id){
        return evaluationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Evaluation with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une evaluation et de l'enregistrement de cette dernière dans la base de données
     * @param evaluation
     * @return l'evaluation créée
     */
    public Evaluation save(Evaluation evaluation){
        return evaluationRepository.save(evaluation);
    }

    /**
     * Methode de mise à jour d'une evaluation dans la base de données
     * @param evaluation
     * @return l'evaluation modifiée
     */
    public void update(Evaluation evaluation){
        String updateQuery = "UPDATE Evaluation SET note = ?, commentaire = ?, utilisateur_id = ? WHERE id = ?";
        jdbcTemplate.update(updateQuery,
                evaluation.getNote(),
                evaluation.getCommentaire(),
                evaluation.getUtilisateur().getId(),
                evaluation.getId());
    }

    /**
     * Methode pour effacer l'ensemble des evaluations dans la base de données
     */
    public void deleteAll(){
        evaluationRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'evaluation en fonction de l'id passé en paramètre.
     * @param id
     * @return l'evaluation qui a été supprimée.
     */
    public Evaluation deleteById(Long id){
        Evaluation evaluation = findById(id);
        evaluationRepository.deleteById(id);
        return evaluation;
    }


}
