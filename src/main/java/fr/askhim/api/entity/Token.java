package fr.askhim.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Data
@Entity
@Table(name = "Token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
	
	@Id
	private String id;

	@Column(nullable = false)
	private LocalDate dateC;

	@Column(nullable = false)
	private LocalDate dateP;

	//Un token pour un utilisateur
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
	private User user;
}
