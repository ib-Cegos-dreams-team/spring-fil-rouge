package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.UtilisateurReduitDTO;
import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, ObjectMapper objectMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des utilisateurs de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'un utilisateurs et que les données restent lisible.
     * @return la liste des utilisateurs enregistrées dans la base de donnée.
     */
    public List<UtilisateurReduitDTO> findAll(){
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        // cette methode permet de mapper le format utilisateur complete au format utilisateur reduite.
        return utilisateurs.stream().map(utilisateur -> objectMapper
                .convertValue(utilisateur, UtilisateurReduitDTO.class)).toList();
    }

    /**
     * Methode qui returne le utilisateur en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return le  utilisateur complete, ou un message d'erreur si pas trouvé
     */
    public Utilisateur findById(Long id){
        return utilisateurRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Utilisateur with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'un utilisateur et de l'enregistrement de cette dernière dans la base de données
     * @param utilisateur
     * @return le utilisateur créé
     */
    public Utilisateur save(Utilisateur utilisateur){
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Methode de mise à jour d'un utilisateur dans la base de données
     * @param utilisateur
     * @return le utilisateur modifié
     */
    public Utilisateur update(Utilisateur utilisateur){
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Methode pour effacer l'ensemble des utilisateurs dans la base de données
     */
    public void deleteAll(){
        utilisateurRepository.deleteAll();
    }

    /**
     * Methode pour supprimer le utilisateur en fonction de l'id passé en paramètre.
     * @param id
     * @return le utilisateur qui a été supprimé.
     */
    public Utilisateur deleteById(Long id){
        Utilisateur utilisateur = findById(id);
        utilisateurRepository.deleteById(id);
        return utilisateur;
    }
}
