package fr.askhim.api.type;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {

    TRANSPORT(1L, "Transport", "http://cdn.askhim.ctrempe.fr/transport.jpg", "http://cdn.askhim.ctrempe.fr/panneauMobile.png"),
    COURSE(2L, "Course", "http://cdn.askhim.ctrempe.fr/course.jpg", "http://cdn.askhim.ctrempe.fr/sacMobile.png"),
    FORMATION(3L, "Formation", "http://cdn.askhim.ctrempe.fr/formation.jpg", "http://cdn.askhim.ctrempe.fr/diplomeMobile.png"),
    LOISIR(4L, "Loisir", "http://cdn.askhim.ctrempe.fr/loisir.jpg", "http://cdn.askhim.ctrempe.fr/loisirMobile.png"),
    TACHE_MENAGERE(5L, "Tâche ménagère", "http://cdn.askhim.ctrempe.fr/tacheMenagere.jpg", "http://cdn.askhim.ctrempe.fr/tacheMenagereMobile.png");

    private Long id;
    private String libelle;
    private String defaultPhoto;
    private String defaultPhotoMobile;
    public static Map<Long, TypeEnum> types = new HashMap<>();

    TypeEnum(Long id, String libelle, String defaultPhoto, String defaultPhotoMobile){
        this.id = id;
        this.libelle = libelle;
        this.defaultPhoto = defaultPhoto;
        this.defaultPhotoMobile = defaultPhotoMobile;
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

    public String getDefaultPhotoMobile(){
        return defaultPhotoMobile;
    }

    public static TypeEnum idToTypeEnum(Long id){
        return types.get(id);
    }

}
