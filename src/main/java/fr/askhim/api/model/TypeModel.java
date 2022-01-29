package fr.askhim.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeModel {

    private Long id;
    private String libelle;
    private String defaultPhoto;
    private String defaultPhotoMobile;
}
