package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.LieuFormationReduiteDTO;
import fr.pafz.spring.ittraining.entity.LieuFormation;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.LieuFormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LieuFormationService {

    private final LieuFormationRepository lieuFormationRepository;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public LieuFormationService(LieuFormationRepository lieuFormationRepository, ObjectMapper objectMapper) {
        this.lieuFormationRepository = lieuFormationRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des lieuFormations de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'informations et que les données restent lisible.
     * @return la liste des lieuFormations enregistrées dans la base de donnée.
     */
    public List<LieuFormationReduiteDTO> findAll(){
        List<LieuFormation> lieuFormations = lieuFormationRepository.findAll();
        // cette methode permet de mapper le format lieuFormation complete au format lieuFormation reduite.
        return lieuFormations.stream().map(lieuFormation -> objectMapper
                .convertValue(lieuFormation, LieuFormationReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne l'lieuFormation en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return le lieuFormation complete, ou un message d'erreur si pas trouvé
     */
    public LieuFormation findById(Long id){
        return lieuFormationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("LieuFormation with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une lieuFormation et de l'enregistrement de cette dernière dans la base de données
     * @param lieuFormation
     * @return le lieuFormation créée
     */
    public LieuFormation save(LieuFormation lieuFormation){
        return lieuFormationRepository.save(lieuFormation);
    }

    /**
     * Methode de mise à jour d'une lieuFormation dans la base de données
     * @param lieuFormation
     * @return l'lieuFormation modifiée
     */
    public LieuFormation update(LieuFormation lieuFormation){
        return lieuFormationRepository.save(lieuFormation);
    }

    /**
     * Methode pour effacer l'ensemble des lieuFormations dans la base de données
     */
    public void deleteAll(){
        lieuFormationRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'lieuFormation en fonction de l'id passé en paramètre.
     * @param id
     * @return le lieuFormation qui a été supprimée.
     */
    public LieuFormation deleteById(Long id){
        LieuFormation lieuFormation = findById(id);
        lieuFormationRepository.deleteById(id);
        return lieuFormation;
    }
}
