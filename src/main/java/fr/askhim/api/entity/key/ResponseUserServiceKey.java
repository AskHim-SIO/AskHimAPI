package fr.askhim.api.entity.key;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public
class ResponseUserServiceKey implements Serializable {

	@Column(name = "user_id")
	Long userId;

	@Column(name = "service_id")
	Long serviceId;

}
