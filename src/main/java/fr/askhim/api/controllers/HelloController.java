package fr.askhim.api.controllers;

import fr.askhim.api.services.AskHimUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.askhim.api.AskHimUser;

@RestController
@RequestMapping("/hello")
public class HelloController {

	private AskHimUserService userService;

	public HelloController(AskHimUserService userService){
		this.userService = userService;
	}
	
	@PostMapping
	public String helloGet(@RequestBody AskHimUser user) {
		userService.registerUser(user.nom, user.prenom);
		return "Hello " + user;
	}

}
