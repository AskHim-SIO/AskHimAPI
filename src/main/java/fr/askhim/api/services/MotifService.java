package fr.askhim.api.services;

import fr.askhim.api.models.entity.typeService.Transport.Motif;
import fr.askhim.api.repository.MotifRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MotifService {

    private final MotifRepository motifRepository;

    public MotifService(MotifRepository motifRepository){
        this.motifRepository = motifRepository;
    }

    public Motif getMotifByLibelle(String libelle){
        return motifRepository.getMotifByLibelle(libelle);
    }

    public boolean motifExist(String libelle){
        return getMotifByLibelle(libelle) != null;
    }

    public Motif getRandomMotif(){
        List<Motif> motifs = motifRepository.findAll();
        if(motifs.size() == 0){
            return null;
        }
        Random random = new Random();
        return motifs.get(random.nextInt(motifs.size()));
    }

}
