package fr.askhim.api.controllers;

import fr.askhim.api.entity.*;
import fr.askhim.api.entity.services.Course;
import fr.askhim.api.entity.services.Formation;
import fr.askhim.api.entity.services.Loisir;
import fr.askhim.api.entity.services.TacheMenagere;
import fr.askhim.api.entity.services.Transport;
import fr.askhim.api.model.CreateServiceModel.*;
import fr.askhim.api.model.Service.CourseModel;
import fr.askhim.api.model.Service.FormationModel;
import fr.askhim.api.model.Service.LoisirModel;
import fr.askhim.api.model.Service.ServiceModel;
import fr.askhim.api.model.Service.TacheMenagereModel;
import fr.askhim.api.model.Service.TransportModel;
import fr.askhim.api.model.ServiceMinModel;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.repository.*;
import fr.askhim.api.services.*;
import fr.askhim.api.type.TypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.Collections.shuffle;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private LoisirRepository loisirRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private TypeRepository typeRepository;

    private final CourseService courseService;
    private final FormationService formationService;
    private final LieuService lieuService;
    private final LoisirService loisirService;
    private final ServiceService serviceService;
    private final TacheMenagereService tacheMenagereService;
    private final TokenService tokenService;
    private final TransportService transportService;
    private final TypeService typeService;

    private ModelMapper mapper = new ModelMapper();

    public ServiceController(CourseService courseService, FormationService formationService, LieuService lieuService, LoisirService loisirService, ServiceService serviceService, TacheMenagereService tacheMenagereService, TokenService tokenService, TransportService transportService, TypeService typeService){
        this.courseService = courseService;
        this.formationService = formationService;
        this.lieuService = lieuService;
        this.loisirService = loisirService;
        this.serviceService = serviceService;
        this.tacheMenagereService = tacheMenagereService;
        this.tokenService = tokenService;
        this.transportService = transportService;
        this.typeService = typeService;
    }

    @GetMapping("/get-services")
    public List<ServiceMinModel> getServices() {
        Page<Service> services = serviceRepository.findAll(Pageable.ofSize(20));
        List<ServiceMinModel> serviceModels = new ArrayList<>();

        services.forEach(service -> {
            serviceModels.add(serviceMinMapToDTO(service));
        });

        shuffle(serviceModels);
        return serviceModels;
    }

    @GetMapping("/get-service/{id}")
    public Object getService(@PathVariable Long id){
        if(!serviceService.serviceExist(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_SERVICE", "Le service spécifié n'existe pas"));
        }
        Service service = serviceService.getServiceById(id);
        TypeEnum serviceType = serviceService.getType(service);
        switch(serviceType){
            case TRANSPORT:
                return transportMapToDTO(service);

            case COURSE:
                return courseMapToDTO(service);

            case FORMATION:
                return formationMapToDTO(service);

            case LOISIR:
                return loisirMapToDTO(service);

            case TACHE_MENAGERE:
                return tacheMenagereMapToDTO(service);

            default:
                return null;
        }
    }

    @GetMapping("/get-services-from-user/{token}")
    public Object getServicesFromUser(@PathVariable UUID token){
        if(!tokenService.tokenExist(token)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }
        Token tokenRecp = tokenService.getTokenById(token);
        User userRecp = tokenRecp.getUser();
        List<Service> services = serviceService.getServicesByUser(userRecp);
        List<ServiceMinModel> servicesMinModel = new ArrayList<>();
        for(Service service : services){
            servicesMinModel.add(serviceMinMapToDTO(service));
        }
        return servicesMinModel;
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


    @PostMapping("/create-transport-service")
    public ResponseEntity createTransportService(@RequestBody CreateTransportModel transportModel){
        Service newService = new Service();
        newService.setName(transportModel.getName());
        newService.setDescription(transportModel.getDescription());
        newService.setDateStart(transportModel.getDateStart());
        newService.setDateEnd(transportModel.getDateEnd());
        newService.setState(true);
        newService.setPrice(transportModel.getPrice());
        newService.setPostDate(new Date());
        newService.setUser(tokenService.getUserByToken(UUID.fromString(transportModel.getUserToken())));
        newService.setType(typeService.getTypeByLibelle(TypeEnum.TRANSPORT.getLibelle()));
        if(lieuService.lieuExist(transportModel.getLieuAdresse(), transportModel.getLieuCodePostal(), transportModel.getLieuVille())){
            newService.setLieu(lieuService.getLieuByTout(transportModel.getLieuAdresse(), transportModel.getLieuCodePostal(), transportModel.getLieuVille()));
        }else{
            Lieu lieu = new Lieu();
            lieu.setAdresse(transportModel.getLieuAdresse());
            lieu.setCodePostal(transportModel.getLieuCodePostal());
            lieu.setVille(transportModel.getLieuVille());
            Lieu lieuN = lieuRepository.save(lieu);
            newService.setLieu(lieuN);
        }
        Service serviceReg = serviceRepository.save(newService);
        Transport newTransport = new Transport();
        newTransport.setPointDepart(transportModel.getPointDepart());;
        newTransport.setPointArriver(transportModel.getPointArriver());
        newTransport.setNbPlaceDispo(transportModel.getNbPlaceDispo());
        newTransport.setVehiculePerso(transportModel.getVehiculePerso());
        newTransport.setMotif(transportModel.getMotif());
        newTransport.setService(serviceReg);
        transportRepository.save(newTransport);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "SERVICE_CREATED", "Le service a bien été enregistré !"));
    }

    @PostMapping("/create-course-service")
    public ResponseEntity createCourseService(@RequestBody CreateCourseModel courseModel){
        Service newService = new Service();
        newService.setName(courseModel.getName());
        newService.setDescription(courseModel.getDescription());
        newService.setDateStart(courseModel.getDateStart());
        newService.setDateEnd(courseModel.getDateEnd());
        newService.setState(true);
        newService.setPrice(courseModel.getPrice());
        newService.setPostDate(new Date());
        newService.setUser(tokenService.getUserByToken(UUID.fromString(courseModel.getUserToken())));
        newService.setType(typeService.getTypeByLibelle(TypeEnum.COURSE.getLibelle()));
        if(lieuService.lieuExist(courseModel.getLieuAdresse(), courseModel.getLieuCodePostal(), courseModel.getLieuVille())){
            newService.setLieu(lieuService.getLieuByTout(courseModel.getLieuAdresse(), courseModel.getLieuCodePostal(), courseModel.getLieuVille()));
        }else{
            Lieu lieu = new Lieu();
            lieu.setAdresse(courseModel.getLieuAdresse());
            lieu.setCodePostal(courseModel.getLieuCodePostal());
            lieu.setVille(courseModel.getLieuVille());
            Lieu lieuN = lieuRepository.save(lieu);
            newService.setLieu(lieuN);
        }
        Service serviceReg = serviceRepository.save(newService);
        Course newCourse = new Course();
        newCourse.setAccompagnement(courseModel.getAccompagnement());
        newCourse.setTypeLieu(courseModel.getTypeLieu());
        newCourse.setService(serviceReg);
        courseRepository.save(newCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "SERVICE_CREATED", "Le service a bien été enregistré !"));
    }

    @PostMapping("/create-formation-service")
    public ResponseEntity createFormationService(@RequestBody CreateFormationModel formationModel){
        Service newService = new Service();
        newService.setName(formationModel.getName());
        newService.setDescription(formationModel.getDescription());
        newService.setDateStart(formationModel.getDateStart());
        newService.setDateEnd(formationModel.getDateEnd());
        newService.setState(true);
        newService.setPrice(formationModel.getPrice());
        newService.setPostDate(new Date());
        newService.setUser(tokenService.getUserByToken(UUID.fromString(formationModel.getUserToken())));
        newService.setType(typeService.getTypeByLibelle(TypeEnum.FORMATION.getLibelle()));
        if(lieuService.lieuExist(formationModel.getLieuAdresse(), formationModel.getLieuCodePostal(), formationModel.getLieuVille())){
            newService.setLieu(lieuService.getLieuByTout(formationModel.getLieuAdresse(), formationModel.getLieuCodePostal(), formationModel.getLieuVille()));
        }else{
            Lieu lieu = new Lieu();
            lieu.setAdresse(formationModel.getLieuAdresse());
            lieu.setCodePostal(formationModel.getLieuCodePostal());
            lieu.setVille(formationModel.getLieuVille());
            Lieu lieuN = lieuRepository.save(lieu);
            newService.setLieu(lieuN);
        }
        Service serviceReg = serviceRepository.save(newService);
        Formation newFormation = new Formation();
        newFormation.setNbHeure(formationModel.getNbHeure());
        newFormation.setPresence(formationModel.getPresence());
        newFormation.setMateriel(formationModel.getMateriel());
        newFormation.setCompetence(formationModel.getCompetence());
        newFormation.setService(serviceReg);
        formationRepository.save(newFormation);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "SERVICE_CREATED", "Le service a bien été enregistré !"));
    }

    @PostMapping("create-loisir-service")
    public ResponseEntity createLoisirService(@RequestBody CreateLoisirModel loisirModel){
        Service newService = new Service();
        newService.setName(loisirModel.getName());
        newService.setDescription(loisirModel.getDescription());
        newService.setDateStart(loisirModel.getDateStart());
        newService.setDateEnd(loisirModel.getDateEnd());
        newService.setState(true);
        newService.setPrice(loisirModel.getPrice());
        newService.setPostDate(new Date());
        newService.setUser(tokenService.getUserByToken(UUID.fromString(loisirModel.getUserToken())));
        newService.setType(typeService.getTypeByLibelle(TypeEnum.LOISIR.getLibelle()));
        if(lieuService.lieuExist(loisirModel.getLieuAdresse(), loisirModel.getLieuCodePostal(), loisirModel.getLieuVille())){
            newService.setLieu(lieuService.getLieuByTout(loisirModel.getLieuAdresse(), loisirModel.getLieuCodePostal(), loisirModel.getLieuVille()));
        }else{
            Lieu lieu = new Lieu();
            lieu.setAdresse(loisirModel.getLieuAdresse());
            lieu.setCodePostal(loisirModel.getLieuCodePostal());
            lieu.setVille(loisirModel.getLieuVille());
            Lieu lieuN = lieuRepository.save(lieu);
            newService.setLieu(lieuN);
        }
        Service serviceReg = serviceRepository.save(newService);
        Loisir newLoisir = new Loisir();
        newLoisir.setNbPersonne(loisirModel.getNbPersonne());
        newLoisir.setAnimal(loisirModel.isAnimal());
        newLoisir.setJeu(loisirModel.getJeu());
        newLoisir.setService(serviceReg);
        loisirRepository.save(newLoisir);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "SERVICE_CREATED", "Le service a bien été enregistré !"));
    }

    @PostMapping("/create-tachemenagere-service")
    public ResponseEntity createTacheMenagereService(@RequestBody CreateTacheMenagereModel tacheMenagereModel){
        Service newService = new Service();
        newService.setName(tacheMenagereModel.getName());
        newService.setDescription(tacheMenagereModel.getDescription());
        newService.setDateStart(tacheMenagereModel.getDateStart());
        newService.setDateEnd(tacheMenagereModel.getDateEnd());
        newService.setState(true);
        newService.setPrice(tacheMenagereModel.getPrice());
        newService.setPostDate(new Date());
        newService.setUser(tokenService.getUserByToken(UUID.fromString(tacheMenagereModel.getUserToken())));
        newService.setType(typeService.getTypeByLibelle(TypeEnum.TACHE_MENAGERE.getLibelle()));
        if(lieuService.lieuExist(tacheMenagereModel.getLieuAdresse(), tacheMenagereModel.getLieuCodePostal(), tacheMenagereModel.getLieuVille())){
            newService.setLieu(lieuService.getLieuByTout(tacheMenagereModel.getLieuAdresse(), tacheMenagereModel.getLieuCodePostal(), tacheMenagereModel.getLieuVille()));
        }else{
            Lieu lieu = new Lieu();
            lieu.setAdresse(tacheMenagereModel.getLieuAdresse());
            lieu.setCodePostal(tacheMenagereModel.getLieuCodePostal());
            lieu.setVille(tacheMenagereModel.getLieuVille());
            Lieu lieuN = lieuRepository.save(lieu);
            newService.setLieu(lieuN);
        }
        Service serviceReg = serviceRepository.save(newService);
        TacheMenagere newTacheMenagere = new TacheMenagere();

        return null;
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
        Transport transport = transportService.getTransportByService(service);
        serviceModel.setPointDepart(transport.getPointDepart());
        serviceModel.setPointArriver(transport.getPointArriver());
        serviceModel.setNbPlaceDispo(transport.getNbPlaceDispo());
        serviceModel.setVehiculePerso(transport.getVehiculePerso());
        serviceModel.setMotif(transport.getMotif());
        return serviceModel;
    }

    private CourseModel courseMapToDTO(Service service) {
        CourseModel serviceModel = mapper.map(service, CourseModel.class);
        Course course = courseService.getCourseByService(service);
        serviceModel.setAccompagnement(course.getAccompagnement());
        serviceModel.setTypeLieu(course.getTypeLieu());
        return serviceModel;
    }

    private FormationModel formationMapToDTO(Service service) {
        FormationModel serviceModel = mapper.map(service, FormationModel.class);
        Formation formation = formationService.getFormationByService(service);
        serviceModel.setNbHeure(formation.getNbHeure());
        serviceModel.setPresence(formation.getPresence());
        serviceModel.setMateriel(formation.getMateriel());
        serviceModel.setCompetence(formation.getCompetence());
        return serviceModel;
    }

    private LoisirModel loisirMapToDTO(Service service) {
        LoisirModel serviceModel = mapper.map(service, LoisirModel.class);
        Loisir loisir = loisirService.getLoisirByService(service);
        serviceModel.setNbPersonne(loisir.getNbPersonne());
        serviceModel.setAnimal(loisir.isAnimal());
        serviceModel.setJeu(loisir.getJeu());
        return serviceModel;
    }

    private TacheMenagereModel tacheMenagereMapToDTO(Service service) {
        TacheMenagereModel serviceModel = mapper.map(service, TacheMenagereModel.class);
        TacheMenagere tacheMenagere = tacheMenagereService.getTacheMenagereService(service);
        serviceModel.setNbHeure(tacheMenagere.getNbHeure());
        serviceModel.setLibelle(tacheMenagere.getLibelle());
        serviceModel.setMateriel(tacheMenagere.getMateriel());
        return serviceModel;
    }
}
