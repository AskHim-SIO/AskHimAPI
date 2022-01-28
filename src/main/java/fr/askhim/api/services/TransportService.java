package fr.askhim.api.services;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.typeService.Transport.Transport;
import fr.askhim.api.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public Transport getTransportByService(Service service){
        return transportRepository.findByService(service);
    }

}
