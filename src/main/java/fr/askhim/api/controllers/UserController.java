package fr.askhim.api.controllers;

import fr.askhim.api.model.UserProfile;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.exception.AppException;
import fr.askhim.api.models.entity.User;
import fr.askhim.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.askhim.api.payload.UserRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	

	@GetMapping("/get-users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/get-user/{id}")
	public User getProduct(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new AppException("L'utilisateur n'a pas été trouvé.");
		}
		return user.get();
	}

	@PostMapping("/create-user")
	public ResponseEntity createUser (@RequestBody UserRequest user) {
		User userEnt = new User();

		userEnt.setName(user.name);
		userEnt.setFirstname(user.firstname);
		userEnt.setAdress(user.adress);
		userEnt.setDateNaiss(user.dateNaiss);
		userEnt.setEmail(user.email);
		userEnt.setPassword(user.password);
		userEnt.setTel(user.tel);

		User result = userRepository.save(userEnt);

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

	@GetMapping("/get-user-profile/{id}")
	public UserProfile getUserProfile(@PathVariable Long id){
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new AppException("L'utilisateur n'a pas été trouvé.");
		}

		User userEnt = user.get();

		UserProfile userProfile = new UserProfile();
		userProfile.setName(userEnt.getName());
		userProfile.setFirstname(userEnt.getFirstname());
		userProfile.setEmail(userEnt.getEmail());
		userProfile.setTel(userEnt.getTel());
		userProfile.setAdress(userEnt.getAdress());
		userProfile.setDateNaiss(userEnt.getDateNaiss());
		userProfile.setCredit(userEnt.getCredit());
		userProfile.setProfilPicture(userEnt.getProfilPicture());

		return userProfile;

	}
}
