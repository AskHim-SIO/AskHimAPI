package fr.askhim.api.model.Service;


import fr.askhim.api.model.Service.ServiceModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportModel extends ServiceModel {

    private String pointDepart;
    private String pointArriver;
    private int nbPlaceDispo;
    private String vehiculePerso;
    private String motif;
}
