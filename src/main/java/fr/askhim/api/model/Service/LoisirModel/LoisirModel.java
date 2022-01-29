package fr.askhim.api.model.Service.LoisirModel;

import fr.askhim.api.model.Service.ServiceModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoisirModel extends ServiceModel {
    private int nbPersonne;
    private String animal;
    private JeuModel jeu;
}
