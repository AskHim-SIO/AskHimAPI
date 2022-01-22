package fr.askhim.api.type;

import fr.askhim.api.models.entity.Type;
import fr.askhim.api.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {

    TRANSPORT(1, "Transport"),
    COURSE(2, "Course"),
    FORMATION(3, "Formation"),
    LOISIR(4, "Loisir"),
    TACHE_MENAGERE(5, "Tâche ménagère");

    private int id;
    private String libelle;
    public static Map<Integer, TypeEnum> types = new HashMap<>();

    TypeEnum(int id, String libelle){
        this.id = id;
        this.libelle = libelle;
    }

    static {
        for(TypeEnum t : TypeEnum.values()){
            types.put(t.getId(), t);
        }
    }

    public int getId(){
        return id;
    }

    public String getLibelle(){
        return libelle;
    }

    public static TypeEnum idToTypeEnum(int id){
        return types.get(id);
    }

}
