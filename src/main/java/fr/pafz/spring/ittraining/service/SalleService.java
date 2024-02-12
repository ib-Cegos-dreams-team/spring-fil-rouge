package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.SalleReduiteDTO;
import fr.pafz.spring.ittraining.entity.Salle;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {

    private final SalleRepository salleRepository;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public SalleService(SalleRepository salleRepository, ObjectMapper objectMapper) {
        this.salleRepository = salleRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des salles de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'insalles et que les données restent lisible.
     * @return la liste des salles enregistrées dans la base de donnée.
     */
    public List<SalleReduiteDTO> findAll(){
        List<Salle> salles = salleRepository.findAll();
        // cette methode permet de mapper le format salle complete au format salle reduite.
        return salles.stream().map(salle -> objectMapper
                .convertValue(salle, SalleReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne l'salle en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return la salle complete, ou un message d'erreur si pas trouvé
     */
    public Salle findById(Long id){
        return salleRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Salle with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une salle et de l'enregistrement de cette dernière dans la base de données
     * @param salle
     * @return l'salle créée
     */
    public Salle save(Salle salle){
        return salleRepository.save(salle);
    }

    /**
     * Methode de mise à jour d'une salle dans la base de données
     * @param salle
     * @return la salle modifiée
     */
    public Salle update(Salle salle){
        return salleRepository.save(salle);
    }

    /**
     * Methode pour effacer l'ensemble des salles dans la base de données
     */
    public void deleteAll(){
        salleRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'salle en fonction de l'id passé en paramètre.
     * @param id
     * @return la salle qui a été supprimée.
     */
    public Salle deleteById(Long id){
        Salle salle = findById(id);
        salleRepository.deleteById(id);
        return salle;
    }
}
