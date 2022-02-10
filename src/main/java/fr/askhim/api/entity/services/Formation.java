package fr.askhim.api.entity.services;

import fr.askhim.api.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Formation")
public class Formation {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int nbHeure;

    @Column(nullable = false)
    private String presence;

    @Column(nullable = false)
    private String materiel;

    @Column(nullable = false)
    private String competence;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
