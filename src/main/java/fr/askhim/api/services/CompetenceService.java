package fr.askhim.api.services;

import fr.askhim.api.entity.typeService.Formation.Competence;
import fr.askhim.api.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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

    public Competence getRandomCompetence(){
        List<Competence> competences = competenceRepository.findAll();
        if(competences.size() == 0){
            return null;
        }
        Random random = new Random();
        return competences.get(random.nextInt(competences.size()));
    }

}
