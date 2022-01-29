package fr.askhim.api.entity;

import lombok.*;

import javax.persistence.*;
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

	@Column(nullable = false)
	private String defaultPhoto;

	@Column(nullable = false)
	private String defaultPhotoMobile;

	@ManyToMany(mappedBy = "types")
	Set<User> prefer;

//	@OneToMany
//	Set<Service> services;



}
