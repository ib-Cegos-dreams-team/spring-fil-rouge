package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.config.JwtProvider;
import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.repository.UtilisateurRepository;
import fr.pafz.spring.ittraining.request.LoginRequest;
import fr.pafz.spring.ittraining.response.AuthResponse;
import fr.pafz.spring.ittraining.service.UtilisateurCustomService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UtilisateurRepository utilisateurRepo;
    private UtilisateurCustomService utilisateurCustomService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public AuthController(UtilisateurRepository utilisateurRepo,
                          UtilisateurCustomService utilisateurCustomService,
                          JwtProvider jwtProvider,
                          PasswordEncoder passwordEncoder) {
        this.utilisateurRepo = utilisateurRepo;
        this.utilisateurCustomService = utilisateurCustomService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    //register method
    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody Utilisateur utilisateur) throws Exception {
        String email = utilisateur.getEmail();
        String password = utilisateur.getPassword();
        String prenom = utilisateur.getPrenom();
        String nom = utilisateur.getNom();
        String telephone = utilisateur.getTelephone();

        Utilisateur isExistEmail = utilisateurRepo.findByEmail(email);
        if (isExistEmail != null) {
            throw new Exception("Email already exists");
        }

        Utilisateur createdUser = new Utilisateur();
        createdUser.setCivilite(utilisateur.getCivilite());
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setPrenom(prenom);
        createdUser.setNom(nom);
        createdUser.setTelephone(telephone);
        Utilisateur savedUser = utilisateurRepo.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        return new AuthResponse(token, "User registered successfully");
    }


    @PostMapping("/signin")
    public AuthResponse signInHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        return new AuthResponse(token, "User signed in successfully");
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = utilisateurCustomService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}