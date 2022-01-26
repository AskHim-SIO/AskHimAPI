package fr.askhim.api.services;

import fr.askhim.api.entity.Service;
import fr.askhim.api.repository.ServiceRepository;
import fr.askhim.api.type.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    private final TypeService typeService;

    public ServiceService(TypeService typeService){
        this.typeService = typeService;
    }

    public List<Service> findByType(TypeEnum typeEnum){
        return serviceRepository.findByType(typeService.getTypeByLibelle(typeEnum.getLibelle()));
    }
}
