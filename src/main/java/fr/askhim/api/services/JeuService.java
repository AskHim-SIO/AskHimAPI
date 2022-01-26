package fr.askhim.api.services;

import fr.askhim.api.entity.typeService.Loisir.Jeu;
import fr.askhim.api.repository.JeuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class JeuService {

    private final JeuRepository jeuRepository;

    public JeuService(JeuRepository jeuRepository){
        this.jeuRepository = jeuRepository;
    }

    public Jeu getJeuByLibelle(String libelle){
        return jeuRepository.findByLibelle(libelle);
    }
    public boolean jeuExist(String libelle){
        return getJeuByLibelle(libelle) != null;
    }

    public Jeu getRandomJeu(){
        List<Jeu> jeux = jeuRepository.findAll();
        if(jeux.size() == 0){
            return null;
        }
        Random random = new Random();
        return jeux.get(random.nextInt(jeux.size()));
    }

}
