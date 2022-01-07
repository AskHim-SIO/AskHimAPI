package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserProfile {

    private String name;
    private String firstname;
    private String email;
    private Long tel;
    private String adress;
    private Date dateNaiss;
    private Number credit;
    private String profilPicture;


}
