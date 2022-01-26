package fr.askhim.api.type;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {

    TRANSPORT(1L, "Transport"),
    COURSE(2L, "Course"),
    FORMATION(3L, "Formation"),
    LOISIR(4L, "Loisir"),
    TACHE_MENAGERE(5L, "Tâche ménagère");

    private Long id;
    private String libelle;
    public static Map<Long, TypeEnum> types = new HashMap<>();

    TypeEnum(Long id, String libelle){
        this.id = id;
        this.libelle = libelle;
    }

    static {
        for(TypeEnum t : TypeEnum.values()){
            types.put(t.getId(), t);
        }
    }

    public Long getId(){
        return id;
    }

    public String getLibelle(){
        return libelle;
    }

    public static TypeEnum idToTypeEnum(Long id){
        return types.get(id);
    }

}
