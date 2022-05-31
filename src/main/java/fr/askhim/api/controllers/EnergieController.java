package fr.askhim.api.controllers;

import fr.askhim.api.entity.Energie;
import fr.askhim.api.entity.Token;
import fr.askhim.api.entity.User;
import fr.askhim.api.model.AuthModel.RegisterModel;
import fr.askhim.api.model.EnergieModel;
import fr.askhim.api.model.TokenModel;
import fr.askhim.api.model.UserModel;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.repository.EnergieRepository;
import fr.askhim.api.repository.TokenRepository;
import fr.askhim.api.repository.UserRepository;
import fr.askhim.api.services.TokenService;
import fr.askhim.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/energie")
public class EnergieController {

    @Autowired
    private EnergieRepository energieRepository;

       private ModelMapper mapper = new ModelMapper();

    @GetMapping("/get-all-energies")
    public List<EnergieModel> getAllEnergies() {
        List<Energie> energies = energieRepository.findAll();
        List<EnergieModel>  energiesModel = new ArrayList<>();

        energies.forEach(energie -> {
            energiesModel.add(mapToDTO(energie));
        });
        return energiesModel;
    }

    // MAPPING
    private EnergieModel mapToDTO(Energie energie) {
        EnergieModel energieModel = mapper.map(energie, EnergieModel.class);
        return energieModel;
    }

//    private TokenModel tokenMapToDTO(Token token) {
//        TokenModel tokenModel = mapper.map(token, TokenModel.class);
//        return tokenModel;
//    }


}
