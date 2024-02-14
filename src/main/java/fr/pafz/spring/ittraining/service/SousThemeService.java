package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.SousThemeReduitDTO;
import fr.pafz.spring.ittraining.dto.ThemeReduitDTO;
import fr.pafz.spring.ittraining.entity.SousTheme;
import fr.pafz.spring.ittraining.entity.Theme;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.SousThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SousThemeService {

    private final SousThemeRepository sousThemeRepository;

    private final JdbcTemplate jdbcTemplate;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public SousThemeService(SousThemeRepository sousThemeRepository, JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.sousThemeRepository = sousThemeRepository;
        this.jdbcTemplate = jdbcTemplate;
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

    /**
     * Methode permettant de remplir la base de donnée avec une liste de themes dans un JSON
     * Cela permet d'eviter de faire une theme à la fois.
     * @param sousThemes : la liste des themes à importer dans la base de données
     */
    public void saveListThemes(List<SousTheme> sousThemes){
        sousThemeRepository.saveAllAndFlush(sousThemes);
    }



    /**
     * methode qui retourne une liste de Themes en fonction de sa catégorie (de l'id catégorie plus précisement)
     * @param idTheme
     * @return Liste de theme reduit DTO
     */
    public List<SousThemeReduitDTO> findByIdTheme(Long idTheme) {
        String findQuery = "SELECT s.id, s.nom, s.description FROM sous_theme s " +
                "INNER JOIN Theme t ON s.theme_id = t.id WHERE t.id = ?";

        // Use parameterized query to prevent SQL injection
        /**
         * Cette sous methode, avec la lambda expression permet de mapper chaque colonne dans un objet theme.
         * Cet objet theme est ensuite ajouté à une liste de themes.
         * et c'est cette liste de theme que la méthode générale envoie
         * rs : est le ResultSet. lorqu'ion fait des requets JDBC
         * rs permet de convertir la valeur récupérée en obkjet JAVA, soit en string, en int, en bool...
         */
        return jdbcTemplate.query(findQuery, new Object[]{idTheme}, (rs, rowNum) -> {
            SousThemeReduitDTO sousThemeReduitDTO = new SousThemeReduitDTO();
            sousThemeReduitDTO.setId(rs.getLong("id"));
            sousThemeReduitDTO.setNom(rs.getNString("nom"));
            sousThemeReduitDTO.setDescription(rs.getNString("description"));
            return sousThemeReduitDTO;
        });
    }
}
