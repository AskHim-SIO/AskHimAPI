package fr.askhim.api.controllers;

import fr.askhim.api.model.Service.CourseModel;
import fr.askhim.api.model.Service.FormationModel.FormationModel;
import fr.askhim.api.model.Service.LoisirModel.LoisirModel;
import fr.askhim.api.model.Service.TacheMenagereModel.TacheMenagereModel;
import fr.askhim.api.model.Service.TransportModel.TransportModel;
import fr.askhim.api.model.ServiceMinModel;
import fr.askhim.api.models.entity.Service;
import fr.askhim.api.models.entity.Type;
import fr.askhim.api.repository.ServiceRepository;
import fr.askhim.api.repository.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.shuffle;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TypeRepository typeRepository;

    private ModelMapper mapper = new ModelMapper();

    @GetMapping("/get-services")
    public List<ServiceMinModel> getServices() {
        Page<Service> services = serviceRepository.findAll(Pageable.ofSize(20));
        List<ServiceMinModel> serviceModels = new ArrayList<>();

        services.forEach(service -> {
            serviceModels.add(serviceMapToDTO(service));
        });

        //todo tester le shuffle
        shuffle(serviceModels);
        return serviceModels;
    }

    @GetMapping("/get-recent-services")
    public List<ServiceMinModel> getRecentServices() {
        //todo tester l'ordre recent
        List<Service> services = serviceRepository.findTop20ByOrderByPostDateDesc();
        List<ServiceMinModel> serviceModels = new ArrayList<>();

        services.forEach(service -> {
            serviceModels.add(serviceMapToDTO(service));
        });

        return serviceModels;
    }


    @GetMapping("/get-transports")
    public List<TransportModel> getTransportsServices() {

        //transport
        Optional<Type> type = typeRepository.findById(Long.valueOf(1));

        List<Service> services = serviceRepository.findByType(type);
        List<TransportModel> transportModels = new ArrayList<>();

        services.forEach(service -> {
            transportModels.add(transportMapToDTO(service));
        });

        return transportModels;
    }

    @GetMapping("/get-courses")
    public List<CourseModel> getCoursesServices() {

        //course
        Optional<Type> type = typeRepository.findById(Long.valueOf(2));

        List<Service> services = serviceRepository.findByType(type);
        List<CourseModel> courseModels = new ArrayList<>();

        services.forEach(service -> {
            courseModels.add(courseMapToDTO(service));
        });

        return courseModels;
    }

    @GetMapping("/get-formations")
    public List<FormationModel> getFormationsServices() {

        //formation
        Optional<Type> type = typeRepository.findById(Long.valueOf(3));

        List<Service> services = serviceRepository.findByType(type);
        List<FormationModel> formationModels = new ArrayList<>();

        services.forEach(service -> {
            formationModels.add(formationMapToDTO(service));
        });

        return formationModels;
    }

    @GetMapping("/get-loisirs")
    public List<LoisirModel> getLoisirsServices() {

        //loisirs
        Optional<Type> type = typeRepository.findById(Long.valueOf(3));

        List<Service> services = serviceRepository.findByType(type);
        List<LoisirModel> loisirModels = new ArrayList<>();

        services.forEach(service -> {
            loisirModels.add(loisirMapToDTO(service));
        });

        return loisirModels;
    }

    @GetMapping("/get-taches-menageres")
    public List<TacheMenagereModel> getTacheMenagereServices() {

        //TacheMenagere
        Optional<Type> type = typeRepository.findById(Long.valueOf(3));

        List<Service> services = serviceRepository.findByType(type);
        List<TacheMenagereModel> tacheMenagereModels = new ArrayList<>();

        services.forEach(service -> {
            tacheMenagereModels.add(tacheMenagereMapToDTO(service));
        });

        return tacheMenagereModels;
    }


    // MAPPING
    private ServiceMinModel serviceMapToDTO(Service service) {
        ServiceMinModel serviceModel = mapper.map(service, ServiceMinModel.class);
        return serviceModel;
    }

    private TransportModel transportMapToDTO(Service service) {
        TransportModel serviceModel = mapper.map(service, TransportModel.class);
        return serviceModel;
    }

    private FormationModel formationMapToDTO(Service service) {
        FormationModel serviceModel = mapper.map(service, FormationModel.class);
        return serviceModel;
    }

    private LoisirModel loisirMapToDTO(Service service) {
        LoisirModel serviceModel = mapper.map(service, LoisirModel.class);
        return serviceModel;
    }

    private TacheMenagereModel tacheMenagereMapToDTO(Service service) {
        TacheMenagereModel serviceModel = mapper.map(service, TacheMenagereModel.class);
        return serviceModel;
    }

    private CourseModel courseMapToDTO(Service service) {
        CourseModel courseModel = mapper.map(service, CourseModel.class);
        return courseModel;
    }
}
