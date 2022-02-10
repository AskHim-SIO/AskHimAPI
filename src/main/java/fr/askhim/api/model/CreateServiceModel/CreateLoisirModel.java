package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLoisirModel extends CreateServiceModel {

    private int nbPersonne;
    private String animal;
    private String jeu;

}
