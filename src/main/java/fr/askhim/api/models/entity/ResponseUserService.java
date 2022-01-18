package fr.askhim.api.models.entity;

import fr.askhim.api.models.entity.key.ResponseUserServiceKey;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


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
