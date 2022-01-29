package fr.askhim.api.services;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.typeService.Formation.Formation;
import fr.askhim.api.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    public Formation getFormationByService(Service service){
        return formationRepository.findByService(service);
    }

}
