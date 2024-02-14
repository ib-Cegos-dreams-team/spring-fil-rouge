package fr.pafz.spring.ittraining.service;

import fr.pafz.spring.ittraining.Enum.Role;
import fr.pafz.spring.ittraining.entity.MyUserDetails;
import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.repository.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service qui permet de charger un utilisateur par son nom d'utilisateur
 * en appelant la méthode loadUserByUsername qui appelle la méthode findByEmail
 */
@Service
public class UtilisateurCustomService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepo;

    public UtilisateurCustomService(UtilisateurRepository utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    /**
     * Méthode qui permet de charger un utilisateur par son nom d'utilisateur
     * en appelant la méthode findByEmail
     * @param username nom d'utilisateur
     * @return UserDetails, qui est une methode de spring security
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Utilisateur user = utilisateurRepo.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé" + username);
        }

        //recuperer le role de l'utilisateur
        Role role = user.getRole();

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role.name());

        return new MyUserDetails(user.getEmail(), user.getPassword(), authorities, role);
    }
}