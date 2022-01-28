package fr.askhim.api.type;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {

    TRANSPORT(1L, "Transport", "http://cdn.askhim.ctrempe.fr/transport.jpg"),
    COURSE(2L, "Course", "http://cdn.askhim.ctrempe.fr/course.jpg"),
    FORMATION(3L, "Formation", "http://cdn.askhim.ctrempe.fr/formation.jpg"),
    LOISIR(4L, "Loisir", "http://cdn.askhim.ctrempe.fr/loisir.jpg"),
    TACHE_MENAGERE(5L, "Tâche ménagère", "http://cdn.askhim.ctrempe.fr/tacheMenagere.jpg");

    private Long id;
    private String libelle;
    private String defaultPhoto;
    public static Map<Long, TypeEnum> types = new HashMap<>();

    TypeEnum(Long id, String libelle, String defaultPhoto){
        this.id = id;
        this.libelle = libelle;
        this.defaultPhoto = defaultPhoto;
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

    public String getDefaultPhoto(){
        return defaultPhoto;
    }

    public static TypeEnum idToTypeEnum(Long id){
        return types.get(id);
    }

}
