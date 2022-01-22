package fr.askhim.api.models.entity.typeService.Loisir;

import fr.askhim.api.models.entity.Service;
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
    private String animal;

    @ManyToOne
    @JoinColumn(name="jeu_id", nullable=false)
    private Jeu jeu;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
