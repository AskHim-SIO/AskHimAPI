package fr.askhim.api.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "Photo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String libelle;

	@ManyToOne
	@JoinColumn(name="service_id", nullable=false)
	private Service service;




}
