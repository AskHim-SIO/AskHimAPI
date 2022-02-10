package fr.askhim.api.model.Service;

import fr.askhim.api.model.Service.ServiceModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TacheMenagereModel extends ServiceModel {

    private int nbHeure;
    private String libelle;
    private String materiel;

}
