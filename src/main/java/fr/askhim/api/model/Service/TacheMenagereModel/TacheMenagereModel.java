package fr.askhim.api.model.Service.TacheMenagereModel;

import fr.askhim.api.model.Service.ServiceModel;

import java.util.ArrayList;
import java.util.List;

public class TacheMenagereModel extends ServiceModel {

    private int nbHeure;
    private String libelle;
    private List<MaterielModel> disposer_de = new ArrayList<>();
}
