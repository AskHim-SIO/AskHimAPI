package fr.askhim.api.controllers;

import com.github.javafaker.Faker;
import fr.askhim.api.models.entity.Lieu;
import fr.askhim.api.models.entity.Type;
import fr.askhim.api.models.entity.User;
import fr.askhim.api.models.entity.typeService.Competence;
import fr.askhim.api.models.entity.typeService.Motif;
import fr.askhim.api.models.entity.typeService.Transport;
import fr.askhim.api.repository.*;
import fr.askhim.api.services.CompetenceService;
import fr.askhim.api.services.LieuService;
import fr.askhim.api.services.MotifService;
import fr.askhim.api.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/seed")
public class SeedController {

    // -- Repositories --

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private MotifRepository motifRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRepository userRepository;

    // -- Dependencies --

    private Faker faker = new Faker();

    // -- Services --

    private final CompetenceService competenceService;

    private final LieuService lieuService;

    private final MotifService motifService;

    private final TypeService typeService;

    // ---------------

    public SeedController(CompetenceService competenceService, LieuService lieuService, MotifService motifService, TypeService typeService){
        this.competenceService = competenceService;
        this.lieuService = lieuService;
        this.motifService = motifService;
        this.typeService = typeService;
    }

    private boolean checkNbSeeds(int nbSeed){
        if(nbSeed > 0) {
            return true;
        }else{
            return false;
        }
    }

    @GetMapping("/seedCompetences")
    public String seedCompetences(int nbSeed){
        if(checkNbSeeds(nbSeed)){
            for(int i = 0 ; i < nbSeed ; i++){
                Competence competenceFaker = new Competence();
                do{
                    competenceFaker.setLibelle(faker.beer().name()); // TODO
                }while(competenceService.competenceExist(competenceFaker.getLibelle()));
                competenceRepository.save(competenceFaker);
            }
            return "[OK] Competences ajoutées";
        }else{
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedLieux")
    public String seedLieux(int nbSeed){
        if(checkNbSeeds(nbSeed)){
            for(int i = 0 ; i < nbSeed ; i++){
                Lieu lieuFaker = new Lieu();
                do{
                    lieuFaker.setVille(faker.address().cityName());
                }while(lieuService.lieuExist(lieuFaker.getVille()));
                lieuRepository.save(lieuFaker);
            }
            return "[OK] Lieux ajoutés";
        }else{
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedMotifs")
    public String seedMotifs(int nbSeed){
        if(checkNbSeeds(nbSeed)){
            for(int i = 0 ; i < nbSeed ; i++){
                Motif motifFaker = new Motif();
                do{
                    motifFaker.setLibelle(faker.beer().name());
                }while(motifService.motifExist(motifFaker.getLibelle()));
                motifRepository.save(motifFaker);
            }
            return "[OK] Motifs ajoutés";
        }else{
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedTransports")
    public String seedTransports(int nbSeed){
        if(checkNbSeeds(nbSeed)){
            for(int i = 0 ; i < nbSeed ; i++){
                Transport transportFaker = new Transport();
                transportFaker.setName(faker.beer().name()); // TODO
                transportFaker.setDateStart(faker.date().birthday(-100, 0));
                transportFaker.setDateEnd(faker.date().birthday(0, 100));
                transportFaker.setPrice((long) faker.number().numberBetween(1, 2000));
            }
            return "[OK] Transports ajoutés";
        }else{
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedTypes")
    public String seedTypes(int nbSeed){
        if(checkNbSeeds(nbSeed)){
            for(int i = 0 ; i < nbSeed ; i++){
                Type typeFaker = new Type();
                do{
                    typeFaker.setLibelle(faker.beer().name()); // TODO
                }while(typeService.typeExist(typeFaker.getLibelle()));
                typeRepository.save(typeFaker);
            }
            return "[OK] Types ajoutés";
        }else{
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/seedUsers")
    public String seedUsers(int nbSeed){
        if(checkNbSeeds(nbSeed)){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            for(int i = 0 ; i < nbSeed ; i++){
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
        }else{
            return "[Error] Le nombre doit être supérieur à 0";
        }
    }

    @GetMapping("/test")
    public String test(String ville){
        if(lieuService.lieuExist(ville)){
            return "[OK]";
        }else{
            return "[Error]";
        }
    }
}
