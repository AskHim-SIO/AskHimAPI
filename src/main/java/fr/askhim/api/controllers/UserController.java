package fr.askhim.api.controllers;

import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.exception.AppException;
import fr.askhim.api.models.entity.AskHimUserEntity;
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
	

	@GetMapping("/get-user")
	public List<AskHimUserEntity> getUser() {
		return userRepository.findAll();
	}

	@GetMapping("/get-user/{id}")
	public AskHimUserEntity getProduct(@PathVariable Long id) {
		Optional<AskHimUserEntity> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new AppException("L'utilisateur n'a pas été trouvé.");
		}
		return user.get();
	}

	@PostMapping("/create-user")
	public ResponseEntity createUser (@RequestBody UserRequest user) {
		AskHimUserEntity userEnt = new AskHimUserEntity();
		userEnt.setNom(user.nom);
		userEnt.setPrenom(user.prenom);
		AskHimUserEntity result = userRepository.save(userEnt);

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
		Optional<AskHimUserEntity> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new AppException("L'utilisateur n'a pas été trouvé.");
		}

		AskHimUserEntity userEnt = user.get();

		userEnt.setNom(request.nom);
		userEnt.setPrenom(request.prenom);

		userRepository.save(userEnt);
		return ResponseEntity.ok()
				.body(new ApiResponse(true,
						"L'utilisateur a été mis à jour."));
	}

	@DeleteMapping("/delete-user/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id) {
		Optional<AskHimUserEntity> userResearch = userRepository.findById(id);
		if (!userResearch.isPresent()) {
			throw new AppException("L'utilisateur n'a pas été trouvé.");
		}
		userRepository.delete(userResearch.get());
		return ResponseEntity.ok()
				.body(new ApiResponse(true,
						"L'utilisateur a été supprimé."));
	}
}
