package fr.askhim.api.controllers;

import fr.askhim.api.entity.Token;
import fr.askhim.api.entity.User;
import fr.askhim.api.model.AuthModel.RegisterModel;
import fr.askhim.api.model.TokenModel;
import fr.askhim.api.model.UserModel;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.repository.TokenRepository;
import fr.askhim.api.repository.UserRepository;
import fr.askhim.api.services.TokenService;
import fr.askhim.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private final UserService userService;

    private final TokenService tokenService;

    private ModelMapper mapper = new ModelMapper();


    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @GetMapping("/get-all-users")
    public List<UserModel> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> usersModel = new ArrayList<>();

        users.forEach(user -> {
            if (user.getDeleteDate() == null)
                usersModel.add(mapToDTO(user));
        });
        return usersModel;
    }

    @GetMapping("/get-nb-users-activated")
    public int getNbUsersActivated() {
        return userService.getNbUserActivated();
    }

    @GetMapping("/get-user-by-token/{token}")
    public Object getUserByToken(@PathVariable UUID token) {
        Optional<Token> tokenRes = tokenRepository.findById(token.toString());
        if (!tokenRes.isPresent()) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }

        Token userTok = tokenRes.get();
        User user = userTok.getUser();
        if (user.getDeleteDate() == null)
            return mapToDTO(user);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "UNKNOWN_TOKEN", "L'utilisateur spécifié n'existe plus"));
    }

    @GetMapping("/get-user-by-id/{id}")
    public Object getUserById(@PathVariable Long id) {
       if(!userService.userExistById(id)){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_USER", "Cet utilisateur n'existe pas"));
       }
       return mapToDTO(userService.getUserById(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody RegisterModel registerModel) {

        if (userService.userExistByEmail(registerModel.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, "USER_ALREADY_EXIST", "Un compte est déjà créé avec cette adresse email."));
        }

        User user = new User();
        user.setName(registerModel.getName());
        user.setFirstname(registerModel.getFirstname());
        user.setEmail(registerModel.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(registerModel.getPassword()));
        user.setDateNaiss(registerModel.getDateNaiss());

        if(registerModel.getTel() != null)
            user.setTel(registerModel.getTel());

        if(registerModel.getAddress() != null)
            user.setAddress(registerModel.getAddress());

        user.setCredit(10L);
        user.setProfilPicture("http://192.168.50.11/cdn/defaultUser.png");


        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "USER_CREATED", "L'utilisateur a été crée."));
    }

    /*@PutMapping("/update-user/{token}")
    public ResponseEntity updateUser(@RequestBody UserRequest request, @PathVariable UUID token) {

        Optional<Token> tokenRes = tokenRepository.findById(token.toString());
        if (!tokenRes.isPresent()) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }

        User user = tokenService.getUserByToken(token);



        user.setFirstname(request.firstname);
        user.setName(request.name);
        user.setAdress(request.adress);
        user.setEmail(request.email);
        user.setDateNaiss(request.dateNaiss);
        user.setTel(request.tel);

        userRepository.save(user);
        // 200
        return ResponseEntity.ok().body(new ApiResponse(true, "USER_UPDATED", "L'utilisateur a été mis à jour."));
    }*/

    @PutMapping("/update-user/{token}")
    public Object updateUser(@PathVariable UUID token, @RequestBody UserModel userModel){
        if(!tokenService.tokenExist(token)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }
        System.out.println("Test");
        User user = DTOToUser(userModel);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(true, "USER_UPDATED", "Le profil de l'utilisateur a été mis à jour avec succès"));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> userResearch = userRepository.findById(id);
        if (!userResearch.isPresent()) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "USER_NOT_FOUND", "L'utilisateur n'a pas été trouvé"));
        }
        Date date = new Date();

        User userEnt = userResearch.get();
        userEnt.setDeleteDate(date);

        userRepository.save(userEnt);

        // 200
        return ResponseEntity.ok().body(new ApiResponse(true, "USER_DELETED", "L'utilisateur a été supprimé."));
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

    private User DTOToUser(UserModel userModel){
        User user = userService.getUserById(userModel.getId());
        user.setName(userModel.getName());
        user.setFirstname(userModel.getFirstname());
        user.setProfilPicture(userModel.getProfilPicture());
        user.setAdmin(userModel.isAdmin());
        user.setEmail(userModel.getEmail());
        user.setTel(userModel.getTel());
        user.setAddress(userModel.getAddress());
        user.setDateNaiss(userModel.getDateNaiss());
        user.setCredit(userModel.getCredit());
        return user;
    }
}
