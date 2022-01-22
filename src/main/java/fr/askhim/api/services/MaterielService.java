package fr.askhim.api.services;

import fr.askhim.api.models.entity.typeService.TacheMenageres.Materiel;
import fr.askhim.api.repository.MaterielRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MaterielService {

    private final MaterielRepository materielRepository;

    public MaterielService(MaterielRepository materielRepository){
        this.materielRepository = materielRepository;
    }

    public Materiel getMaterielByLibelle(String libelle){
        return materielRepository.getMaterielByLibelle(libelle);
    }

    public boolean materielExist(String libelle){
        return getMaterielByLibelle(libelle) != null;
    }

    public Materiel getRandomMateriel(){
        List<Materiel> materiels = materielRepository.findAll();
        if(materiels.size() == 0){
            return null;
        }
        Random random = new Random();
        return materiels.get(random.nextInt(materiels.size()));
    }
}
