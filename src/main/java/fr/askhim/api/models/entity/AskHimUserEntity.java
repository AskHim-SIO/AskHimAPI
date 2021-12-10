package fr.askhim.api.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AskHimUserEntity {
	
	@Id
	@GeneratedValue
	private int id;
	@SuppressWarnings("unused")
	private String nom;
	@SuppressWarnings("unused")
	private String prenom;

}
