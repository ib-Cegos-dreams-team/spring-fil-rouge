package fr.pafz.spring.ittraining.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pafz.spring.ittraining.dto.ThemeReduitDTO;
import fr.pafz.spring.ittraining.entity.Theme;
import fr.pafz.spring.ittraining.exception.NotFoundException;
import fr.pafz.spring.ittraining.repository.ThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    private final JdbcTemplate jdbcTemplate;

    // mapper pour faire le lien avec les classes DTO
    private final ObjectMapper objectMapper;

    public ThemeService(ThemeRepository themeRepository, JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.themeRepository = themeRepository;
        this.jdbcTemplate = jdbcTemplate;
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

    /**
     * Methode permettant de remplir la base de donnée avec une liste de themes dans un JSON
     * Cela permet d'eviter de faire une theme à la fois.
     * @param themes : la liste des themes à importer dans la base de données
     */
    public void saveListThemes(List<Theme> themes){
        themeRepository.saveAllAndFlush(themes);
    }



    /**
     * methode qui retourne une liste de Themes en fonction de sa catégorie (de l'id catégorie plus précisement)
     * @param idCategorie
     * @return Liste de theme reduit DTO
     */
    public List<ThemeReduitDTO> findByIdCategorie(Long idCategorie) {
        String findQuery = "SELECT t.id, t.nom, t.description FROM Theme t " +
                "INNER JOIN Categorie c ON t.categorie_id = c.id WHERE c.id = ?";

        // Use parameterized query to prevent SQL injection
        /**
         * Cette sous methode, avec la lambda expression permet de mapper chaque colonne dans un objet theme.
         * Cet objet theme est ensuite ajouté à une liste de themes.
         * et c'est cette liste de theme que la méthode générale envoie
         * rs : est le ResultSet. lorqu'ion fait des requets JDBC
         * rs permet de convertir la valeur récupérée en obkjet JAVA, soit en string, en int, en bool...
         */
        return jdbcTemplate.query(findQuery, new Object[]{idCategorie}, (rs, rowNum) -> {
            ThemeReduitDTO theme = new ThemeReduitDTO();
            theme.setId(rs.getLong("id"));
            theme.setNom(rs.getNString("nom"));
            theme.setDescription(rs.getNString("description"));
            return theme;
        });
    }




}
