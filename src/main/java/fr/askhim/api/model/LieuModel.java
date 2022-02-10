package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LieuModel {

    private Long id;
    private String adresse;
    private int codePostal;
    private String ville;

}
