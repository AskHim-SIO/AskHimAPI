package fr.askhim.api.controllers;

import fr.askhim.api.exception.AppException;
import fr.askhim.api.model.AuthModel.LoginModel;
import fr.askhim.api.model.AuthModel.RegisterModel;
import fr.askhim.api.model.UserModel;
import fr.askhim.api.models.entity.User;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.payload.UserRequest;
import fr.askhim.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping("/get-users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get-user/{id}")
    public UserModel getUserProfile(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new AppException("L'utilisateur n'a pas été trouvé.");
        }

        User userEnt = user.get();

        return mapToDTO(userEnt);
    }

    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody RegisterModel user) {
        User userEnt = new User();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        User result = userRepository.save(DTOToMapForRegister(user));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true,
                        "L'utilisateur a été crée."));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity updateUser(@RequestBody UserRequest request, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new AppException("L'utilisateur n'a pas été trouvé.");
        }

        User userEnt = user.get();

        userEnt.setName(request.name);
        userEnt.setFirstname(request.firstname);

        userRepository.save(userEnt);
        return ResponseEntity.ok()
                .body(new ApiResponse(true,
                        "L'utilisateur a été mis à jour."));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> userResearch = userRepository.findById(id);
        if (!userResearch.isPresent()) {
            throw new AppException("L'utilisateur n'a pas été trouvé.");
        }
        userRepository.delete(userResearch.get());
        return ResponseEntity.ok()
                .body(new ApiResponse(true,
                        "L'utilisateur a été supprimé."));
    }

    @PostMapping("login")
    public User loginUser(@RequestBody LoginModel request) {
        List<User> users = userRepository.findByEmail(request.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        for (User user : users) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return user;
            }
        }
        User newuser = new User();
        return newuser;
    }

    // MAPPING
    private UserModel mapToDTO(User user) {
        UserModel userModel = mapper.map(user, UserModel.class);
        return userModel;
    }

    private User DTOToMap(UserModel userModel) {
        User user = mapper.map(userModel, User.class);
        return user;
    }

    private User DTOToMapForRegister(RegisterModel userModel) {
        User user = mapper.map(userModel, User.class);
        return user;
    }
}
