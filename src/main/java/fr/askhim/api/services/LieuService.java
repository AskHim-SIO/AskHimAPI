package fr.askhim.api.services;

import fr.askhim.api.entity.Lieu;
import fr.askhim.api.repository.LieuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LieuService {

    private final LieuRepository lieuRepository;

    public LieuService(LieuRepository lieuRepository){
        this.lieuRepository = lieuRepository;
    }


    /*public Lieu getLieuByVille(String ville){
        return lieuRepository.findByVille(ville);
    }*/

    public Lieu getLieuByTout(String adresse, int codePostal, String ville){
        return lieuRepository.findByAdresseAndCodePostalAndVille(adresse, codePostal, ville);
    }

    public boolean lieuExist(String adresse, int codePostal, String ville){
        return getLieuByTout(adresse, codePostal, ville) != null;
    }

    public Lieu getRandomLieu(){
        List<Lieu> lieux = lieuRepository.findAll();
        if(lieux.size() == 0){
            return null;
        }
        Random random = new Random();
        return lieux.get(random.nextInt(lieux.size()));
    }


}
