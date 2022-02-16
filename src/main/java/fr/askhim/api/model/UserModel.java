package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserModel {

    private Long id;
    private String name;
    private String firstname;
    private String profilPicture;
    private boolean isAdmin;
    public String email;
    public Long tel;
    public String adress;
    public Date dateNaiss;

}
