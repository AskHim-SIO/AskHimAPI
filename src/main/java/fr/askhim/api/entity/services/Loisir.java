package fr.askhim.api.entity.services;

import fr.askhim.api.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Loisir")
public class Loisir {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int nbPersonne;

    @Column(nullable = false)
    private boolean animal;

    @Column(nullable = false)
    private String jeu;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
