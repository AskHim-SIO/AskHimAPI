package fr.askhim.api.controllers;

import com.github.javafaker.Faker;
import fr.askhim.api.entity.*;
import fr.askhim.api.entity.services.Course;
import fr.askhim.api.entity.services.Formation;
import fr.askhim.api.entity.services.Loisir;
import fr.askhim.api.entity.services.TacheMenagere;
import fr.askhim.api.entity.services.Transport;
import fr.askhim.api.repository.*;
import fr.askhim.api.services.*;
import fr.askhim.api.type.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private LoisirRepository loisirRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TacheMenagereRepository tacheMenagereRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRepository userRepository;

    // -- Dependencies --

    private Faker faker = new Faker();

    // -- Services --

    private final LieuService lieuService;

    private final TypeService typeService;

    private final UserService userService;

    // ---------------

    public SeedController(LieuService lieuService, TypeService typeService, UserService userService) {
        this.lieuService = lieuService;
        this.typeService = typeService;
        this.userService = userService;
    }

    private boolean checkNbSeeds(int nbSeed) {
        if (nbSeed > 0) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/seedcourses")
    public String seedCourses(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            for (int i = 0; i < nbSeed; i++) {
                Service newServiceEntity = buildServiceSeeded(TypeEnum.COURSE);
                Course courseFaker = new Course();
                courseFaker.setService(newServiceEntity);
                courseFaker.setAccompagnement(faker.beer().name()); // TODO
                courseFaker.setListeCourse(faker.beer().name()); // TODO
                courseFaker.setTypeLieu(faker.beer().name()); // TODO
                courseRepository.save(courseFaker);
            }
            return "[OK] Courses ajoutées";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedformations")
    public String seedFormations(int nbSeed){
        if (checkNbSeeds(nbSeed)){
            for (int i = 0; i < nbSeed; i++){
                Service newServiceEntity = buildServiceSeeded(TypeEnum.FORMATION);
                Formation formationFaker = new Formation();
                formationFaker.setService(newServiceEntity);
                formationFaker.setNbHeure(faker.random().nextInt(2, 16));
                formationFaker.setPresence(faker.beer().name()); // TODO
                formationFaker.setMateriel(faker.beer().name()); // TODO
                formationFaker.setCompetence(faker.beer().name()); // TODO
                formationRepository.save(formationFaker);
            }
            return "[OK] Formations ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedlieux")
    public String seedLieux(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            for (int i = 0; i < nbSeed; i++) {
                Lieu lieuFaker = new Lieu();
                do {
                    lieuFaker.setAdresse(faker.address().streetAddress());
                    lieuFaker.setCodePostal(Integer.parseInt(faker.address().zipCode().replace("-", "")));
                    lieuFaker.setVille(faker.address().cityName());
                } while (lieuService.lieuExist(lieuFaker.getAdresse(), lieuFaker.getCodePostal(), lieuFaker.getVille()));
                lieuRepository.save(lieuFaker);
            }
            return "[OK] Lieux ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedloisirs")
    public String seedLoisirs(int nbSeed){
        if (checkNbSeeds(nbSeed)){
            for (int i = 0; i < nbSeed; i++){
                Service newServiceEntity = buildServiceSeeded(TypeEnum.LOISIR);
                Loisir loisirFaker = new Loisir();
                loisirFaker.setService(newServiceEntity);
                loisirFaker.setNbPersonne(faker.random().nextInt(2, 20));
                Random random = new Random();
                int rdmResult = random.nextInt(2);
                loisirFaker.setAnimal((rdmResult == 0) ? true : false);
                loisirFaker.setJeu(faker.beer().name()); // TODO
                loisirRepository.save(loisirFaker);
            }
            return "[OK] Loisirs ajoutés";
        }else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedtachesmenagere")
    public String seedtachesMenagere(int nbSeed){
        if (checkNbSeeds(nbSeed)){
            for (int i = 0; i < nbSeed; i++){
                Service newServiceEntity = buildServiceSeeded(TypeEnum.TACHE_MENAGERE);
                TacheMenagere tacheMenagereFaker = new TacheMenagere();
                tacheMenagereFaker.setService(newServiceEntity);
                tacheMenagereFaker.setNbHeure(faker.random().nextInt(1,6));
                tacheMenagereFaker.setLibelle(faker.beer().name()); // TODO
                tacheMenagereFaker.setMateriel(faker.beer().name()); // TODO
                TacheMenagere tacheMenagereNew = tacheMenagereRepository.save(tacheMenagereFaker);
            }
            return "[OK] Tâches ménagère ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedtransports")
    public String seedTransports(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            for (int i = 0; i < nbSeed; i++) {
                Service newServiceEntity = buildServiceSeeded(TypeEnum.TRANSPORT);
                Transport transportFaker = new Transport();
                transportFaker.setService(newServiceEntity);
                transportFaker.setPointDepart(faker.address().fullAddress());
                transportFaker.setPointArriver(faker.address().fullAddress());
                transportFaker.setNbPlaceDispo(faker.random().nextInt(1, 4));
                transportFaker.setVehiculePerso(faker.beer().name()); // TODO
                transportFaker.setMotif(faker.beer().name()); // TODO
                transportRepository.save(transportFaker);
            }
            return "[OK] Transports ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedusers")
    public String seedUsers(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            for (int i = 0; i < nbSeed; i++) {
                User userFake = new User();
                userFake.setName(faker.name().lastName());
                userFake.setFirstname(faker.name().firstName());
                userFake.setPassword(passwordEncoder.encode("azerty"));
                userFake.setEmail(faker.internet().emailAddress());
                userFake.setTel(0605040302L);
                userFake.setAdress(faker.address().fullAddress());
                userFake.setDateNaiss(faker.date().birthday(18, 100));
                userFake.setCredit((long) new Random().nextInt(1000));
                userFake.setProfilPicture(faker.avatar().image());
                userRepository.save(userFake);
            }
            return "[OK] Utilisateurs ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/test")
    public String test() {
        return faker.address().zipCode();
    }

    private Service buildServiceSeeded(TypeEnum typeEnum){
        Service serviceEntityFaker = new Service();
        serviceEntityFaker.setName(faker.beer().name()); // TODO
        serviceEntityFaker.setDescription(faker.beer().name()); // TODO
        serviceEntityFaker.setDateStart(faker.date().birthday(-100, 0));
        serviceEntityFaker.setDateEnd(faker.date().birthday(0, 100));
        serviceEntityFaker.setPrice((long) faker.number().numberBetween(1, 50));
        serviceEntityFaker.setPostDate(new Date());
        serviceEntityFaker.setUser(userService.getRandomUser());
        serviceEntityFaker.setType(typeRepository.getTypeByLibelle(typeEnum.getLibelle()));
        serviceEntityFaker.setLieu(lieuService.getRandomLieu());
        Photo photo = new Photo();
        photo.setLibelle(typeEnum.getDefaultPhoto());
        Service newService = serviceRepository.save(serviceEntityFaker);
        photo.setService(newService);
        photoRepository.save(photo);
        return newService;
    }
}
