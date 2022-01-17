package fr.askhim.api.services;

import fr.askhim.api.models.entity.Lieu;
import fr.askhim.api.repository.LieuRepository;
import org.springframework.stereotype.Service;

@Service
public class LieuService {

    private final LieuRepository lieuRepository;

    public LieuService(LieuRepository lieuRepository){
        this.lieuRepository = lieuRepository;
    }


    public Lieu getLieuByVille(String ville){
        return lieuRepository.findByVille(ville);
    }
    public boolean lieuExist(String ville){
        return getLieuByVille(ville) != null;
    }
}
