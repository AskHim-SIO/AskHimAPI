package fr.askhim.api.entity.typeService.TacheMenageres;

import fr.askhim.api.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="TacheMenagere")
public class TacheMenagere {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int nbHeure;

    @Column(nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "tacheMenageres")
    List<Materiel> disposer_de;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
