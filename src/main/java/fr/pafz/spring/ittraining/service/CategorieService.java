package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.CategorieReduiteDTO;
import fr.pafz.spring.ittraining.entity.Categorie;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.CategorieRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    private final JdbcTemplate jdbcTemplate;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public CategorieService(CategorieRepository categorieRepository, JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.categorieRepository = categorieRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des categories de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'informations et que les données restent lisible.
     * @return la liste des categories enregistrées dans la base de donnée.
     */
    public List<CategorieReduiteDTO> findAll(){
        List<Categorie> categories = categorieRepository.findAll();
        // cette methode permet de mapper le format categorie complete au format categorie reduite.
        return categories.stream().map(categorie -> objectMapper
                .convertValue(categorie, CategorieReduiteDTO.class)).toList();
    }

    /**
     * Methode qui returne l'categorie en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return la categorie complete, ou un message d'erreur si pas trouvé
     */
    public Categorie findById(Long id){
        return categorieRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Categorie with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une categorie et de l'enregistrement de cette dernière dans la base de données
     * @param categorie
     * @return la categorie créée
     */
    public Categorie save(Categorie categorie){
        return categorieRepository.save(categorie);
    }

    /**
     * Methode de mise à jour d'une categorie dans la base de données
     * @param categorie
     * @return la categorie modifiée
     */
    public void update(Categorie categorie){
        String updateQuery = "UPDATE Categorie SET nom = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(updateQuery,
                categorie.getNom(),
                categorie.getDescription(),
                categorie.getId());
    }

    /**
     * Methode pour effacer l'ensemble des categories dans la base de données
     */
    public void deleteAll(){
        categorieRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'categorie en fonction de l'id passé en paramètre.
     * @param id
     * @return la categorie qui a été supprimée.
     */
    public Categorie deleteById(Long id){
        Categorie categorie = findById(id);
        categorieRepository.deleteById(id);
        return categorie;
    }



}
