package fr.askhim.api.services;

import fr.askhim.api.models.entity.typeService.Motif;
import fr.askhim.api.repository.MotifRepository;
import org.springframework.stereotype.Service;

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

}
