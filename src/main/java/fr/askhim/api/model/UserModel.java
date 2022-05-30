package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Getter
@Setter

public class UserModel {

    private Long id;
    private String name;
    private String firstname;
    private String profilPicture;
    private boolean admin;
    private String email;
    private Long tel;
    private String address;
    private Date dateNaiss;
    private Long credit;

}
