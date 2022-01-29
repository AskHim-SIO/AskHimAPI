package fr.askhim.api.model.Service.FormationModel;

import fr.askhim.api.model.Service.ServiceModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormationModel extends ServiceModel {
    private int nbHeure;
    private String presence;
    private String materiel;
    private CompetenceModel competence;
}
