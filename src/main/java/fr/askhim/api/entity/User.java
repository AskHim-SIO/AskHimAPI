package fr.askhim.api.entity;

import javax.persistence.*;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "User")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column()
	private Long tel;

	@Column()
	private String adress;

	@Column()
	@Temporal(TemporalType.DATE)
	private Date dateNaiss;

	@Column(columnDefinition = "long default 0")
	private Long credit;

	@Column
	private String profilPicture;

	@ManyToMany
	@JoinTable(
			name = "Favoris",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "service_id"))
	Set<Service> favoris;

	@ManyToMany
	@JoinTable(
			name = "Prefer",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "type_id"))
	Set<Type> types;

	//Un user repond a un service
	@OneToMany(mappedBy = "user")
	Set<ResponseUserService> response;

	//Un user demande des services
//	@OneToMany(mappedBy="user")
//	private Set<Service> services;

	//un user peut avoir plusieurs tokens
	@OneToMany(mappedBy="user")
	private Set<Token> tokens;
}
