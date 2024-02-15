package fr.pafz.spring.ittraining.service;

import fr.pafz.spring.ittraining.config.JwtProvider;
import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.repository.UtilisateurRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImplementation implements UtilisateurService {

    private UtilisateurRepository utilisateurRepo;

    private JwtProvider jwtProvider;

    public UtilisateurServiceImplementation(UtilisateurRepository utilisateurRepo, JwtProvider jwtProvider) {
        this.utilisateurRepo = utilisateurRepo;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Utilisateur findUserById(Long id) throws Exception {
        return utilisateurRepo.findById(id).orElseThrow(() -> new Exception("Utilisateur non trouvé"));
    }

    @Override
    public Utilisateur findUserByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        if(email==null){
            throw new Exception("Invalid token");
        }

        Utilisateur user = utilisateurRepo.findByEmail(email);
        if(user==null){
            throw new Exception("User not found");
        }

        return user;
    }

    @Override
    public Utilisateur deleteUserById(Long id) throws Exception {
        Utilisateur user = findUserById(id);
        utilisateurRepo.delete(user);
        return user;
    }
}
