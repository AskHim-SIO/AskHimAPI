package fr.askhim.api.services;

import fr.askhim.api.models.entity.Type;
import fr.askhim.api.repository.TypeRepository;
import org.springframework.stereotype.Service;

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
}
