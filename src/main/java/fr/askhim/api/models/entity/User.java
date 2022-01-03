package fr.askhim.api.models.entity;

import javax.persistence.*;

import fr.askhim.api.models.entity.key.ResponseUserServiceKey;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private Long tel;

	@Column(nullable = false)
	private String adress;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date  dateNaiss;

	@Column(columnDefinition = "integer default 0")
	private Number credit;

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
	@OneToMany(mappedBy="user")
	private Set<Service> services;

}