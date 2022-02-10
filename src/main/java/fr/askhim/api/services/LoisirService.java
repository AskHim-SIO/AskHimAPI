package fr.askhim.api.services;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.services.Loisir;
import fr.askhim.api.repository.LoisirRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class LoisirService {

    @Autowired
    private LoisirRepository loisirRepository;

    public Loisir getLoisirByService(Service service){
        return loisirRepository.findByService(service);
    }

}
