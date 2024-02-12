package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.AdresseReduiteDTO;
import fr.pafz.spring.ittraining.entity.Adresse;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.AdresseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

    private final AdresseRepository adresseRepository;

    private final JdbcTemplate jdbcTemplate;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public AdresseService(AdresseRepository adresseRepository, JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.adresseRepository = adresseRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des adresses de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'informations et que les données restent lisible.
     * @return la liste des adresses enregistrées dans la base de donnée.
     */
    public List<AdresseReduiteDTO> findAll(){
       List<Adresse> adresses = adresseRepository.findAll();
       // cette methode permet de mapper le format adresse complete au format adresse reduite.
       return adresses.stream().map(adresse -> objectMapper
               .convertValue(adresse, AdresseReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne l'adresse en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return l'adresse complete, ou un message d'erreur si pas trouvé
     */
    public Adresse findById(Long id){
        return adresseRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Adresse with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une adresse et de l'enregistrement de cette dernière dans la base de données
     * @param adresse
     * @return l'adresse créée
     */
    public Adresse save(Adresse adresse){
        return adresseRepository.save(adresse);
    }

    /**
     * Methode de mise à jour d'une adresse dans la base de données
     * @param adresse
     * @return l'adresse modifiée
     */
    public void update(Adresse adresse){
    String updateQuery = "UPDATE Adresse SET code_postal = ?, rue = ?, ville = ?, pays = ? WHERE id = ?";
        jdbcTemplate.update(updateQuery,
                adresse.getCodePostal(),
                adresse.getRue(),
                adresse.getVille(),
                adresse.getPays(),
                adresse.getId());
    }

    /**
     * Methode pour effacer l'ensemble des adresses dans la base de données
     */
    public void deleteAll(){
        adresseRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'adresse en fonction de l'id passé en paramètre.
     * @param id
     * @return l'adresse qui a été supprimée.
     */
    public Adresse deleteById(Long id){
        Adresse adresse = findById(id);
        adresseRepository.deleteById(id);
        return adresse;
    }


}
