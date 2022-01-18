package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TokenModel {

    private String id;
    private LocalDate dateC;
    private LocalDate dateP;
    private UserModel user;

}
