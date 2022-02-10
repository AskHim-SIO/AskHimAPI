package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFormationModel extends CreateServiceModel {

    private int nbHeure;
    private String presence;
    private String materiel;
    private String competence;

}
