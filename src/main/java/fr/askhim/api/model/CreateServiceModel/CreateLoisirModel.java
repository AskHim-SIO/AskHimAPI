package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLoisirModel extends CreateServiceModel {

    private int nbPersonne;
    private boolean animal;
    private String jeu;

}
