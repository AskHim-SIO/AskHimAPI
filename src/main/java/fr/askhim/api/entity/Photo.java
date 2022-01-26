package fr.askhim.api.entity;

import lombok.*;

import javax.persistence.*;

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
