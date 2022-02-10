package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTacheMenagereModel extends CreateServiceModel {

    private int nbHeure;
    private String libelle;
    private String materiel;

}
