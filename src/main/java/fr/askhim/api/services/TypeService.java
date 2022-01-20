package fr.askhim.api.services;

import fr.askhim.api.models.entity.Type;
import fr.askhim.api.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository){
        this.typeRepository = typeRepository;
    }

    public Type getTypeByLibelle(String libelle){
        return typeRepository.getTypeByLibelle(libelle);
    }

    public boolean typeExist(String libelle){
        return getTypeByLibelle(libelle) != null;
    }

    public Type getRandomType(){
        List<Type> types = typeRepository.findAll();
        if(types.size() == 0){
            return null;
        }
        Random random = new Random();
        return types.get(random.nextInt(types.size()));
    }
}
