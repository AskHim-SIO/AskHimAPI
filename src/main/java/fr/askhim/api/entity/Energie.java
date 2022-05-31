package fr.askhim.api.entity;

import fr.askhim.api.entity.services.Transport;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "Energie")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Energie {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String libelle;

	@OneToMany(mappedBy="energie")
	private Set<Transport> transports;

}
