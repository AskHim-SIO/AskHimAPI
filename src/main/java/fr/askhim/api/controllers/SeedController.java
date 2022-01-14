package fr.askhim.api.controllers;

import com.github.javafaker.Faker;
import fr.askhim.api.models.entity.User;
import fr.askhim.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/seedUsers")
    public String seedUser(int nbSeed){
        if(nbSeed > 0){
            Faker faker = new Faker();
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
            return "Utilisateurs créé";
        }else{
            return "Le nombre doit être supérieur à 0";
        }
    }
}
