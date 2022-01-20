package fr.askhim.api.services;

import fr.askhim.api.models.entity.typeService.Competence;
import fr.askhim.api.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    public CompetenceService(CompetenceRepository competenceRepository){
        this.competenceRepository = competenceRepository;
    }

    public Competence getCompetenceByLibelle(String libelle){
        return competenceRepository.findByLibelle(libelle);
    }

    public boolean competenceExist(String libelle){
        return getCompetenceByLibelle(libelle) != null;
    }

}
