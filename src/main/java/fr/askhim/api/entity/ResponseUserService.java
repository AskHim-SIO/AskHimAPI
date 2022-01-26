package fr.askhim.api.entity;

import fr.askhim.api.entity.key.ResponseUserServiceKey;

import javax.persistence.*;
import java.util.Date;


@Entity
public class ResponseUserService {

	@EmbeddedId
	ResponseUserServiceKey id;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne
	@MapsId("serviceId")
	@JoinColumn(name = "service_id")
	Service service;

	Date date;
	String libelle;
	Boolean vu;
}
