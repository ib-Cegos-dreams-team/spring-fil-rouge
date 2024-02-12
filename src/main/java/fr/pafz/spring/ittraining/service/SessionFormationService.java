package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.SessionFormationReduiteDTO;
import fr.pafz.spring.ittraining.entity.SessionFormation;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.SessionFormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionFormationService {
    private final SessionFormationRepository sessionFormationRepository;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public SessionFormationService(SessionFormationRepository sessionFormationRepository, ObjectMapper objectMapper) {
        this.sessionFormationRepository = sessionFormationRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des sessions de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'insessions et que les données restent lisible.
     * @return la liste des sessions enregistrées dans la base de donnée.
     */
    public List<SessionFormationReduiteDTO> findAll(){
        List<SessionFormation> sessions = sessionFormationRepository.findAll();
        // cette methode permet de mapper le format session complete au format session reduite.
        return sessions.stream().map(session -> objectMapper
                .convertValue(session, SessionFormationReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne l'session en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return la session complete, ou un message d'erreur si pas trouvé
     */
    public SessionFormation findById(Long id){
        return sessionFormationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Formation with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une session et de l'enregistrement de cette dernière dans la base de données
     * @param session
     * @return l'session créée
     */
    public SessionFormation save(SessionFormation session){
        return sessionFormationRepository.save(session);
    }

    /**
     * Methode de mise à jour d'une session dans la base de données
     * @param session
     * @return la session modifiée
     */
    public SessionFormation update(SessionFormation session){
        return sessionFormationRepository.save(session);
    }

    /**
     * Methode pour effacer l'ensemble des sessions dans la base de données
     */
    public void deleteAll(){
        sessionFormationRepository.deleteAll();
    }

    /**
     * Methode pour supprimer la session en fonction de l'id passé en paramètre.
     * @param id
     * @return la session qui a été supprimée.
     */
    public SessionFormation deleteById(Long id){
        SessionFormation session = findById(id);
        sessionFormationRepository.deleteById(id);
        return session;
    }


}
