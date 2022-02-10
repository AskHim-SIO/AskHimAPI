package fr.askhim.api.model.Service;

import fr.askhim.api.model.Service.ServiceModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoisirModel extends ServiceModel {
    private int nbPersonne;
    private boolean animal;
    private String jeu;
}
