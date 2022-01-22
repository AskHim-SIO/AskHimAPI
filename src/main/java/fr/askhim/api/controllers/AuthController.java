package fr.askhim.api.controllers;

import fr.askhim.api.model.AuthModel.LoginModel;
import fr.askhim.api.models.entity.Token;
import fr.askhim.api.models.entity.User;
import fr.askhim.api.repository.TokenRepository;
import fr.askhim.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("login")
    public String loginUser(@RequestBody LoginModel request) {
        List<User> users = userRepository.findByEmail(request.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User userConnect = null;
        for (User user : users) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

                userConnect = user;
            }
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
        return null;
    }


    @GetMapping("token-valid")
    public Boolean tokenValid(@RequestParam String request) {
        if (request == null) {
            return false;
        }

        Optional<Token> tokenResult = tokenRepository.findById(request);

        if (tokenResult.isPresent() == false) {
            return false;
        }

        Token token = tokenResult.get();
        LocalDate dateP = token.getDateP();

        if (dateP.isBefore(LocalDate.now())) {
            tokenRepository.delete(token);
        }

        return true;

    }

}