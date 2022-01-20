package fr.askhim.api.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "Lieu")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lieu {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String ville;

	//une ville pour plusieurs services
//	@OneToMany(mappedBy="lieu")
//	private Set<Service> services;

}
