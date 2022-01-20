package fr.askhim.api.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "Type")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Type {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String libelle;

	@ManyToMany(mappedBy = "types")
	Set<User> prefer;

//	@OneToMany
//	Set<Service> services;



}
