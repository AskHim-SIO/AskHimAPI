package fr.askhim.api.controllers;

import com.github.javafaker.Faker;
import fr.askhim.api.entity.*;
import fr.askhim.api.entity.typeService.Course;
import fr.askhim.api.entity.typeService.Formation.Competence;
import fr.askhim.api.entity.typeService.Formation.Formation;
import fr.askhim.api.entity.typeService.Loisir.Jeu;
import fr.askhim.api.entity.typeService.Loisir.Loisir;
import fr.askhim.api.entity.typeService.TacheMenageres.Materiel;
import fr.askhim.api.entity.typeService.TacheMenageres.TacheMenagere;
import fr.askhim.api.entity.typeService.Transport.Motif;
import fr.askhim.api.entity.typeService.Transport.Transport;
import fr.askhim.api.repository.*;
import fr.askhim.api.services.*;
import fr.askhim.api.type.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/seed")
public class SeedController {

    // -- Repositories --

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private JeuRepository jeuRepository;

    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private LoisirRepository loisirRepository;

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private MotifRepository motifRepository;

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

    private final CompetenceService competenceService;

    private final JeuService jeuService;

    private final LieuService lieuService;

    private final MaterielService materielService;

    private final MotifService motifService;

    private final TypeService typeService;

    private final UserService userService;

    // ---------------

    public SeedController(CompetenceService competenceService, JeuService jeuService, LieuService lieuService, MaterielService materielService, MotifService motifService, TypeService typeService, UserService userService) {
        this.competenceService = competenceService;
        this.jeuService = jeuService;
        this.lieuService = lieuService;
        this.materielService = materielService;
        this.motifService = motifService;
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

    @GetMapping("/seedcompetences")
    public String seedCompetences(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            for (int i = 0; i < nbSeed; i++) {
                Competence competenceFaker = new Competence();
                do {
                    competenceFaker.setLibelle(faker.beer().name()); // TODO
                } while (competenceService.competenceExist(competenceFaker.getLibelle()));
                competenceRepository.save(competenceFaker);
            }
            return "[OK] Competences ajoutées";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
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
                courseFaker.setTypeLieu(faker.beer().name()); // TODO
                courseFaker.setAdresseLieu(faker.random().nextInt(1, 50)); // TODO
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
                formationFaker.setCompetence(competenceService.getRandomCompetence());
                formationRepository.save(formationFaker);
            }
            return "[OK] Formations ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedjeux")
    public String seedJeux(int nbSeed){
        if (checkNbSeeds(nbSeed)){
            for (int i = 0; i < nbSeed; i++){
                Jeu jeuFaker = new Jeu();
                do{
                    jeuFaker.setLibelle(faker.beer().name()); // TODO
                }while(jeuService.jeuExist(jeuFaker.getLibelle()));
                jeuRepository.save(jeuFaker);
            }
            return "[OK] Jeux ajoutés";
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
                    lieuFaker.setVille(faker.address().cityName());
                } while (lieuService.lieuExist(lieuFaker.getVille()));
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
                loisirFaker.setAnimal(faker.beer().name()); // TODO
                loisirFaker.setJeu(jeuService.getRandomJeu());
                loisirRepository.save(loisirFaker);
            }
            return "[OK] Loisirs ajoutés";
        }else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedmateriels")
    public String seedMateriels(int nbSeed){
        if (checkNbSeeds(nbSeed)){
            for (int i = 0; i < nbSeed; i++){
                Materiel materielFaker = new Materiel();
                do {
                    materielFaker.setLibelle(faker.beer().name()); // TODO
                }while(materielService.materielExist(materielFaker.getLibelle()));
                materielRepository.save(materielFaker);
            }
            return "[OK] Materiels ajoutés";
        }else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedmotifs")
    public String seedMotifs(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            for (int i = 0; i < nbSeed; i++) {
                Motif motifFaker = new Motif();
                do {
                    motifFaker.setLibelle(faker.beer().name());
                } while (motifService.motifExist(motifFaker.getLibelle()));
                motifRepository.save(motifFaker);
            }
            return "[OK] Motifs ajoutés";
        } else {
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
                List<Materiel> materiels = new ArrayList<>();
                materiels.add(materielService.getRandomMateriel());
                tacheMenagereFaker.setDisposer_de(materiels);
                tacheMenagereRepository.save(tacheMenagereFaker);
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
                transportFaker.setMotif(motifService.getRandomMotif());
                transportRepository.save(transportFaker);
            }
            return "[OK] Transports ajoutés";
        } else {
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedtypes")
    public String seedTypes(int nbSeed) {
        if (checkNbSeeds(nbSeed)) {
            for (int i = 0; i < nbSeed; i++) {
                Type typeFaker = new Type();
                do {
                    typeFaker.setLibelle(faker.beer().name()); // TODO
                } while (typeService.typeExist(typeFaker.getLibelle()));
                typeRepository.save(typeFaker);
            }
            return "[OK] Types ajoutés";
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
        return userService.getRandomUser() + "";
    }

    private Service buildServiceSeeded(TypeEnum typeEnum){
        Service serviceEntityFaker = new Service();
        serviceEntityFaker.setName(faker.beer().name()); // TODO
        serviceEntityFaker.setDateStart(faker.date().birthday(-100, 0));
        serviceEntityFaker.setDateEnd(faker.date().birthday(0, 100));
        serviceEntityFaker.setPrice((long) faker.number().numberBetween(1, 2000));
        serviceEntityFaker.setPostDate(new Date());
        serviceEntityFaker.setUser(userService.getRandomUser());
        serviceEntityFaker.setType(typeRepository.getTypeByLibelle(typeEnum.getLibelle()));
        serviceEntityFaker.setLieu(lieuService.getRandomLieu());
        Photo photo = new Photo();
        switch(typeEnum){
            case TRANSPORT:
                photo.setLibelle("http://cdn.askhim.ctrempe.fr/transport.jpg");
                break;

            case COURSE:
                photo.setLibelle("http://cdn.askhim.ctrempe.fr/course.jpg");
                break;

            case FORMATION:
                photo.setLibelle("http://cdn.askhim.ctrempe.fr/formation.jpg");
                break;

            case LOISIR:
                photo.setLibelle("http://cdn.askhim.ctrempe.fr/loisir.jpg");
                break;

            case TACHE_MENAGERE:
                photo.setLibelle("http://cdn.askhim.ctrempe.fr/tacheMenagere.jpg");
                break;
        }
        Service newService = serviceRepository.save(serviceEntityFaker);
        photo.setService(newService);
        photoRepository.save(photo);
        return newService;
    }
}
