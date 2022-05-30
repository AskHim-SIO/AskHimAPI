package fr.askhim.api.services;

import fr.askhim.api.entity.Lieu;
import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.User;
import fr.askhim.api.repository.ServiceRepository;
import fr.askhim.api.repository.TransportRepository;
import fr.askhim.api.type.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TransportRepository transportRepository;

    private final TypeService typeService;

    public ServiceService(TypeService typeService){
        this.typeService = typeService;
    }

    public Service getServiceById(Long id){
        Optional<Service> serviceTry = serviceRepository.findById(id);
        return (serviceTry.isPresent() ? serviceTry.get() : null);
    }

    public List<Service> getServicesByType(TypeEnum typeEnum){
        return serviceRepository.findByType(typeService.getTypeByLibelle(typeEnum.getLibelle()));
    }

    public List<Service> getServicesByUser(User user){
        return serviceRepository.findByUser(user);
    }

    public boolean serviceExist(Long id){
        return getServiceById(id) != null;
    }

    public List<Service> getServiceByLieuCodePostal (int postalCode){
        return serviceRepository.findByLieuCodePostal(postalCode);
    }

    public TypeEnum getType(Service service){
        return TypeEnum.idToTypeEnum(service.getType().getId());
    }


    public List<Service> getServiceByCountyCode (int countyCode){
        List<Service> services = serviceRepository.findAll();

        List<Service> truncateLieu = new ArrayList<>();

        for(Service service : services){
            String cpStr = "" + service.getLieu().getCodePostal();
            String cpSubtr = cpStr.substring(0, 5 - 3);
            if(cpSubtr.equals(countyCode + "")) truncateLieu.add(service);
        }

        return truncateLieu;
    }
}
