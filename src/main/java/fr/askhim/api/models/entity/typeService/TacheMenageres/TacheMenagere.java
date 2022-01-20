package fr.askhim.api.models.entity.typeService.TacheMenageres;

import fr.askhim.api.models.entity.Service;
import fr.askhim.api.models.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="TacheMenagere")
public class TacheMenagere extends Service {

    @Column(nullable = false)
    private int nbHeure;

    @Column(nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "tacheMenageres")
    List<Materiel> disposer_de;
}
