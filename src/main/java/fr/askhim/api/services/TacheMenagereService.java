package fr.askhim.api.services;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.services.TacheMenagere;
import fr.askhim.api.repository.TacheMenagereRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TacheMenagereService {

    @Autowired
    private TacheMenagereRepository tacheMenagereRepository;

    public TacheMenagere getTacheMenagereService(Service service){
        return tacheMenagereRepository.findByService(service);
    }

}
