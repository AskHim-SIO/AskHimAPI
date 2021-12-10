package fr.askhim.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.askhim.api.AskHimUser;

@RestController
@RequestMapping("/hello")
public class HelloController {
	
	@PostMapping
	public String helloGet(@RequestBody AskHimUser user, String test) {
		return "Hello " + user;
	}

}
