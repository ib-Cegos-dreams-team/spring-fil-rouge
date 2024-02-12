package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.SousThemeReduitDTO;
import fr.pafz.spring.ittraining.entity.SousTheme;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.SousThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SousThemeService {

    private final SousThemeRepository sousThemeRepository;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public SousThemeService(SousThemeRepository sousThemeRepository, ObjectMapper objectMapper) {
        this.sousThemeRepository = sousThemeRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Methode qui retourne l'ensemble des sousThemes de la base de donnée au format reduit.
     * Ceci afin de ne pas surcharger notre front-end d'insousThemes et que les données restent lisible.
     * @return la liste des sousThemes enregistrées dans la base de donnée.
     */
    public List<SousThemeReduitDTO> findAll(){
        List<SousTheme> sousThemes = sousThemeRepository.findAll();
        // cette methode permet de mapper le format sousTheme complete au format sousTheme reduite.
        return sousThemes.stream().map(sousTheme -> objectMapper
                .convertValue(sousTheme, SousThemeReduitDTO.class)).toList();
    }

    /**
     * Methode qui returne la sousTheme en fonction de l'Id. La methode orElseThrow prend en compte une fonction lambda
     * cette fonction anonyme "()" créée un nouvel erreur NotFoundException avec le
     * message en paramètre
     * @param id
     * @return la sousTheme complete, ou un message d'erreur si pas trouvé
     */
    public SousTheme findById(Long id){
        return sousThemeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("SousTheme with id : "+ id+" not found."));
    }

    /**
     * Methode de création d'une sousTheme et de l'enregistrement de cette dernière dans la base de données
     * @param sousTheme
     * @return l'sousTheme créée
     */
    @Transactional
    public SousTheme save(SousTheme sousTheme){
        return sousThemeRepository.save(sousTheme);
    }

    /**
     * Methode de mise à jour d'une sousTheme dans la base de données
     * @param sousTheme
     * @return la sousTheme modifiée
     */
    public SousTheme update(SousTheme sousTheme){
        return sousThemeRepository.save(sousTheme);
    }

    /**
     * Methode pour effacer l'ensemble des sousThemes dans la base de données
     */
    public void deleteAll(){
        sousThemeRepository.deleteAll();
    }

    /**
     * Methode pour supprimer l'sousTheme en fonction de l'id passé en paramètre.
     * @param id
     * @return la sousTheme qui a été supprimée.
     */
    public SousTheme deleteById(Long id){
        SousTheme sousTheme = findById(id);
        sousThemeRepository.deleteById(id);
        return sousTheme;
    }
}
