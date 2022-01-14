package fr.askhim.api.models.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

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
