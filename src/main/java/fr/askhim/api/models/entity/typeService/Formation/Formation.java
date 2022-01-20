package fr.askhim.api.models.entity.typeService.Formation;

import fr.askhim.api.models.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Formation")
public class Formation extends Service {

    @Column(nullable = false)
    private int nbHeure;

    @Column(nullable = false)
    private String presence;

    @Column(nullable = false)
    private String materiel;


    @ManyToOne
    @JoinColumn(name="competence_id", nullable=false)
    private Competence competence;
}
