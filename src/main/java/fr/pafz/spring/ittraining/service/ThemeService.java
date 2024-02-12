package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.ThemeReduitDTO;
import fr.pafz.spring.ittraining.entity.Theme;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public ThemeService(ThemeRepository themeRepository, ObjectMapper objectMapper) {
        this.themeRepository = themeRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des themes de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'un themes et que les données restent lisible.
     * @return la liste des themes enregistrées dans la base de donnée.
     */
    public List<ThemeReduitDTO> findAll(){
        List<Theme> themes = themeRepository.findAll();
        // cette methode permet de mapper le format theme complete au format theme reduite.
        return themes.stream().map(theme -> objectMapper
                .convertValue(theme, ThemeReduitDTO.class)).toList();
    }

    /**
     * Methode qui returne le theme en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return le  theme complete, ou un message d'erreur si pas trouvé
     */
    public Theme findById(Long id){
        return themeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Theme with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'un theme et de l'enregistrement de cette dernière dans la base de données
     * @param theme
     * @return le theme créé
     */
    public Theme save(Theme theme){
        return themeRepository.save(theme);
    }

    /**
     * Methode de mise à jour d'un theme dans la base de données
     * @param theme
     * @return le theme modifié
     */
    public Theme update(Theme theme){
        return themeRepository.save(theme);
    }

    /**
     * Methode pour effacer l'ensemble des themes dans la base de données
     */
    public void deleteAll(){
        themeRepository.deleteAll();
    }

    /**
     * Methode pour supprimer le theme en fonction de l'id passé en paramètre.
     * @param id
     * @return le theme qui a été supprimé.
     */
    public Theme deleteById(Long id){
        Theme theme = findById(id);
        themeRepository.deleteById(id);
        return theme;
    }
}
