package fr.askhim.api.services;

import org.springframework.stereotype.Service;

import fr.askhim.api.models.entity.AskHimUserEntity;

@Service
public class AskHimUserService {
	
	public void registerUser(String nom, String prenom) {
		
		AskHimUserEntity entityToDB = AskHimUserEntity.builder().nom(nom).prenom(prenom).build();
		
	}

}
