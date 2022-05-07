package fr.askhim.api.type;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {

    TRANSPORT(1L, "Transport", "http://192.168.49.11/transport.jpg", "http://192.168.49.11/panneauMobile.png"),
    COURSE(2L, "Course", "http://192.168.49.11/course.jpg", "http://192.168.49.11/sacMobile.png"),
    FORMATION(3L, "Formation", "http://192.168.49.11/formation.jpg", "http://192.168.49.11/diplomeMobile.png"),
    LOISIR(4L, "Loisir", "http://192.168.49.11/loisir.jpg", "http://192.168.49.11/loisirMobile.png"),
    TACHE_MENAGERE(5L, "Tâche ménagère", "http://192.168.49.11/tacheMenagere.jpg", "http://192.168.49.11/tacheMenagereMobile.png");

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
