package fr.askhim.api.entity.services;

import fr.askhim.api.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String materiel;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
