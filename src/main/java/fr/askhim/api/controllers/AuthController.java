package fr.askhim.api.controllers;

import fr.askhim.api.model.AuthModel.LoginModel;
import fr.askhim.api.entity.Token;
import fr.askhim.api.entity.User;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.repository.TokenRepository;
import fr.askhim.api.repository.UserRepository;
import fr.askhim.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("login")
    public String loginUser(HttpServletResponse response, @RequestBody LoginModel request) {
        boolean checkPass = true;
        if(!userService.userExistByEmail(request.getEmail())){
            checkPass = false;
        }
        if(checkPass){
            User user = userRepository.findByEmail(request.getEmail());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User userConnect = null;

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                userConnect = user;
            }

            if (userConnect != null) {
                UUID uuid = UUID.randomUUID();

                LocalDate dateCreation = LocalDate.now();
                LocalDate dateExpiration = LocalDate.now().plusMonths(1);

                Token token = new Token();

                token.setId(uuid.toString());
                token.setDateC(dateCreation);
                token.setDateP(dateExpiration);
                token.setUser(userConnect);

                tokenRepository.save(token);

                return token.getId();
            }
        }
        // 401
        response.setStatus(HttpStatus.UNAUTHORIZED.value(), "LOGINS_INVALID");
        return null;
    }


    @GetMapping("token-valid")
    public ResponseEntity tokenValid(@RequestParam String request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "MISSING_PARAMETER", "Des arguments de la requête sont manquants"));
        }

        Optional<Token> tokenResult = tokenRepository.findById(request);

        if (tokenResult.isPresent() == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "TOKEN_NOT_FOUND", "Le token spécifié n'existe pas"));
        }

        Token token = tokenResult.get();
        LocalDate dateP = token.getDateP();

        if (dateP.isBefore(LocalDate.now())) {
            tokenRepository.delete(token);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "TOKEN_NOT_FOUND", "Le token spécifié n'existe pas"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "TOKEN_EXIST", "Le token spécifié existe"));

    }

}
