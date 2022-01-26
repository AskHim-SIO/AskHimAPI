package fr.askhim.api.controllers;

import fr.askhim.api.model.AuthModel.RegisterModel;
import fr.askhim.api.model.TokenModel;
import fr.askhim.api.model.UserModel;
import fr.askhim.api.entity.Token;
import fr.askhim.api.entity.User;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.payload.UserRequest;
import fr.askhim.api.repository.TokenRepository;
import fr.askhim.api.repository.UserRepository;
import fr.askhim.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    // -- Repositories --

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    // -- Services --

    private final UserService userService;

    // -- Others --

    private ModelMapper mapper = new ModelMapper();


    public UserController(UserService userService){
        this.userService = userService;
    }



    @GetMapping("/get-users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get-user/{token}")
    public Object getUserProfile(@PathVariable String token) {
        Optional<Token> tokenRes = tokenRepository.findById(token);
        if (!tokenRes.isPresent()) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "USER_NOT_FOUND", "L'utilisateur n'a pas été trouvé"));
        }

        Token userTok = tokenRes.get();
        User user = userTok.getUser();

        return mapToDTO(user);
    }

    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody RegisterModel user) {

        if(userService.userExist(user.getEmail())){
            // 409
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, "USER_ALREADY_EXIST", "Un compte est déjà créé avec cette adresse email."));
        }

        User userEnt = new User();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        User result = userRepository.save(DTOToMapForRegister(user));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        // 201
        return ResponseEntity.created(location).body(new ApiResponse(true, "USER_CREATED", "L'utilisateur a été crée."));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity updateUser(@RequestBody UserRequest request, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "USER_NOT_FOUND", "L'utilisateur n'a pas été trouvé"));
        }

        User userEnt = user.get();

        userEnt.setName(request.name);
        userEnt.setFirstname(request.firstname);

        userRepository.save(userEnt);
        // 200
        return ResponseEntity.ok().body(new ApiResponse(true, "USER_UPDATED", "L'utilisateur a été mis à jour."));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> userResearch = userRepository.findById(id);
        if (!userResearch.isPresent()) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "USER_NOT_FOUND", "L'utilisateur n'a pas été trouvé"));
        }
        userRepository.delete(userResearch.get());
        // 200
        return ResponseEntity.ok().body(new ApiResponse(true, "USER_DELETED","L'utilisateur a été supprimé."));
    }





    // MAPPING
    private UserModel mapToDTO(User user) {
        UserModel userModel = mapper.map(user, UserModel.class);
        return userModel;
    }

    private TokenModel tokenMapToDTO(Token token) {
        TokenModel tokenModel = mapper.map(token, TokenModel.class);
        return tokenModel;
    }

    private User DTOToMapForRegister(RegisterModel userModel) {
        User user = mapper.map(userModel, User.class);
        return user;
    }
}
