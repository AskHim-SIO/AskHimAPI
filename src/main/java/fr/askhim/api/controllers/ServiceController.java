package fr.askhim.api.controllers;

import fr.askhim.api.entity.Token;
import fr.askhim.api.entity.User;
import fr.askhim.api.model.Service.CourseModel;
import fr.askhim.api.model.Service.FormationModel.FormationModel;
import fr.askhim.api.model.Service.LoisirModel.LoisirModel;
import fr.askhim.api.model.Service.ServiceModel;
import fr.askhim.api.model.Service.TacheMenagereModel.TacheMenagereModel;
import fr.askhim.api.model.Service.TransportModel.TransportModel;
import fr.askhim.api.model.ServiceMinModel;
import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.Type;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.repository.ServiceRepository;
import fr.askhim.api.repository.TypeRepository;
import fr.askhim.api.services.ServiceService;
import fr.askhim.api.services.TokenService;
import fr.askhim.api.services.TypeService;
import fr.askhim.api.type.TypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.shuffle;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TypeRepository typeRepository;

    private final ServiceService serviceService;
    private final TokenService tokenService;
    private final TypeService typeService;

    private ModelMapper mapper = new ModelMapper();

    public ServiceController(ServiceService serviceService, TokenService tokenService, TypeService typeService){
        this.serviceService = serviceService;
        this.tokenService = tokenService;
        this.typeService = typeService;
    }

    @GetMapping("/get-services")
    public List<ServiceMinModel> getServices() {
        Page<Service> services = serviceRepository.findAll(Pageable.ofSize(20));
        List<ServiceMinModel> serviceModels = new ArrayList<>();

        services.forEach(service -> {
            serviceModels.add(serviceMinMapToDTO(service));
        });

        //todo tester le shuffle
        shuffle(serviceModels);
        return serviceModels;
    }

    /*@GetMapping("/get-service/{id}")
    public Object getService(@PathVariable Long id){
        if(!serviceService.serviceExist(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_SERVICE", "Le service spécifié n'existe pas"));
        }
        Service service = serviceService.getServiceById(id);
        TypeEnum serviceType = serviceService.getType(service);
        TransportModel modelRtn = new TransportModel();
        switch(serviceType){
            case TRANSPORT:
                //modelRtn
                break;
        }
    }*/

    @GetMapping("/get-services-from-user/{token}")
    public Object getServicesFromUser(@PathVariable UUID token){
        if(!tokenService.tokenExist(token)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }
        Token tokenRecp = tokenService.getTokenById(token);
        User userRecp = tokenRecp.getUser();
        List<Service> services = serviceService.getServicesByUser(userRecp);
        List<ServiceModel> servicesModel = new ArrayList<>();
        for(Service service : services){
            servicesModel.add(serviceMapToDTO(service));
        }
        return servicesModel;
    }

    @GetMapping("/get-services-from-type/{typeId}")
    public Object getServicesFromType(@PathVariable Long typeId){
            Optional<Type> type = typeRepository.findById(typeId);
            if(!type.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TYPE", "Le type spécifié est introuvable"));
            }

            List<Service> services = serviceService.getServicesByType(TypeEnum.idToTypeEnum(typeId));
            List<ServiceMinModel> servicesMinModel = new ArrayList<>();

            for(Service service : services){
                servicesMinModel.add(serviceMinMapToDTO(service));
            }

            return servicesMinModel;
    }

    @GetMapping("/get-recent-services")
    public List<ServiceMinModel> getRecentServices() {
        //todo tester l'ordre recent
        List<Service> serviceEntities = serviceRepository.findTop20ByOrderByPostDateDesc();
        List<ServiceMinModel> serviceModels = new ArrayList<>();

        serviceEntities.forEach(service -> {
            serviceModels.add(serviceMinMapToDTO(service));
        });

        return serviceModels;
    }


    /*@GetMapping("/get-transports")
    public List<TransportModel> getTransportsServices() {

        //transport
        Type type = typeRepository.findById(TypeEnum.TRANSPORT.getId()).get();

        List<Service> serviceEntities = serviceRepository.findByType(type);
        List<TransportModel> transportModels = new ArrayList<>();

        serviceEntities.forEach(service -> {
            transportModels.add(transportMapToDTO(service));
        });

        return transportModels;
    }

    @GetMapping("/get-courses")
    public List<CourseModel> getCoursesServices() {

        //course
        Type type = typeRepository.findById(TypeEnum.COURSE.getId()).get();

        List<Service> serviceEntities = serviceRepository.findByType(type);
        List<CourseModel> courseModels = new ArrayList<>();

        serviceEntities.forEach(service -> {
            courseModels.add(courseMapToDTO(service));
        });

        return courseModels;
    }

    @GetMapping("/get-formations")
    public List<FormationModel> getFormationsServices() {

        //formation
        Type type = typeRepository.findById(TypeEnum.FORMATION.getId()).get();

        List<Service> serviceEntities = serviceRepository.findByType(type);
        List<FormationModel> formationModels = new ArrayList<>();

        serviceEntities.forEach(service -> {
            formationModels.add(formationMapToDTO(service));
        });

        return formationModels;
    }

    @GetMapping("/get-loisirs")
    public List<LoisirModel> getLoisirsServices() {

        //loisirs
        Type type = typeRepository.findById(TypeEnum.LOISIR.getId()).get();

        List<Service> serviceEntities = serviceRepository.findByType(type);
        List<LoisirModel> loisirModels = new ArrayList<>();

        serviceEntities.forEach(service -> {
            loisirModels.add(loisirMapToDTO(service));
        });

        return loisirModels;
    }

    @GetMapping("/get-taches-menageres")
    public List<TacheMenagereModel> getTacheMenagereServices() {

        //TacheMenagere
        Type type = typeRepository.findById(TypeEnum.TACHE_MENAGERE.getId()).get();

        List<Service> serviceEntities = serviceRepository.findByType(type);
        List<TacheMenagereModel> tacheMenagereModels = new ArrayList<>();

        serviceEntities.forEach(service -> {
            tacheMenagereModels.add(tacheMenagereMapToDTO(service));
        });

        return tacheMenagereModels;
    }*/


    // MAPPING
    private ServiceMinModel serviceMinMapToDTO(Service service) {
        ServiceMinModel serviceModel = mapper.map(service, ServiceMinModel.class);
        return serviceModel;
    }

    private ServiceModel serviceMapToDTO(Service service) {
        ServiceModel serviceModel = mapper.map(service, ServiceModel.class);
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
