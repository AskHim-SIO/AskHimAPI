package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransportModel extends CreateServiceModel{

    private String pointDepart;
    private String pointArriver;
    private int nbPlaceDispo;
    private String vehiculePerso;
    private String motif;
    private String energie;

}
