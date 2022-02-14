package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    private Long id;
    private String name;
    private String firstname;
    private String profilPicture;
    private boolean isAdmin;


}
