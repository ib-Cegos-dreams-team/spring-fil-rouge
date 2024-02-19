package fr.pafz.spring.ittraining.controller;

import fr.pafz.spring.ittraining.Enum.Role;
import fr.pafz.spring.ittraining.config.JwtProvider;
import fr.pafz.spring.ittraining.entity.Utilisateur;
import fr.pafz.spring.ittraining.repository.UtilisateurRepository;
import fr.pafz.spring.ittraining.request.LoginRequest;
import fr.pafz.spring.ittraining.response.AuthResponse;
import fr.pafz.spring.ittraining.service.UtilisateurCustomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Utilisateur> createUser(@RequestBody Utilisateur utilisateur) {
        try {
            String email = utilisateur.getEmail();
            String password = utilisateur.getPassword();
            String prenom = utilisateur.getPrenom();
            String nom = utilisateur.getNom();
            String telephone = utilisateur.getTelephone();

            // Check for existing email
            Optional<Utilisateur> isExistEmail = Optional.ofNullable(utilisateurRepo.findByEmail(email));
            if (isExistEmail.isPresent()) {
                return ResponseEntity.badRequest().body(null); // Or throw a specific exception
            }

            // Create and save user
            Utilisateur createdUser = new Utilisateur();
            createdUser.setCivilite(utilisateur.getCivilite());
            createdUser.setEmail(email);
            createdUser.setPassword(passwordEncoder.encode(password));
            createdUser.setPrenom(prenom);
            createdUser.setNom(nom);
            createdUser.setTelephone(telephone);
            createdUser.setRole(Role.valueOf("UTILISATEUR"));
            Utilisateur savedUser = utilisateurRepo.save(createdUser);

            Authentication authentication = authenticate(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }



    @PostMapping("/signin")
    public AuthResponse signInHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String role = utilisateurRepo.findByEmail(username).getRole().toString();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        return new AuthResponse(token, "User signed in successfully", role);
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

    @GetMapping("/utilisateurs")
    public Iterable<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepo.findAll();
    }

    @DeleteMapping("/utilisateurs/{id}")
    public void deleteUserById(@PathVariable Long id) throws Exception {
        Utilisateur user = utilisateurRepo.findById(id).orElseThrow(() -> new Exception("Utilisateur non trouvé"));
        if(user.getRole() == Role.ADMIN){
            throw new Exception("Vous ne pouvez pas supprimer un admin");
        }
        utilisateurRepo.delete(user);
    }

    @PostMapping("/enregistrer")
    public ResponseEntity<Utilisateur> createUserFromAdmin(@RequestBody Utilisateur utilisateur) {
        try {
            String email = utilisateur.getEmail();
            String password = utilisateur.getPassword();
            String prenom = utilisateur.getPrenom();
            String nom = utilisateur.getNom();
            String telephone = utilisateur.getTelephone();
            Role role = utilisateur.getRole();

            // Check for existing email
            Optional<Utilisateur> isExistEmail = Optional.ofNullable(utilisateurRepo.findByEmail(email));
            if (isExistEmail.isPresent()) {
                return ResponseEntity.badRequest().body(null); // Or throw a specific exception
            }

            // Create and save user
            Utilisateur createdUser = new Utilisateur();
            createdUser.setCivilite(utilisateur.getCivilite());
            createdUser.setEmail(email);
            createdUser.setPassword(passwordEncoder.encode(password));
            createdUser.setPrenom(prenom);
            createdUser.setNom(nom);
            createdUser.setTelephone(telephone);
            createdUser.setRole(role);
            Utilisateur savedUser = utilisateurRepo.save(createdUser);

            Authentication authentication = authenticate(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


}