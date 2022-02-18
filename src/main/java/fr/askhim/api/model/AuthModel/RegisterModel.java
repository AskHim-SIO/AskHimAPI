package fr.askhim.api.model.AuthModel;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegisterModel {

    private String name;
    private String firstname;
    private String email;
    private String password;
    private Date dateNaiss;
    private Long tel;
    private String address;
}
