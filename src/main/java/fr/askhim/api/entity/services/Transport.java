package fr.askhim.api.entity.services;

import fr.askhim.api.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Transport")
public class Transport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String pointDepart;

    @Column(nullable = false)
    private String pointArriver;

    @Column(nullable = false)
    private int nbPlaceDispo;

    @Column(nullable = false)
    private String vehiculePerso;

    @Column(nullable = false)
    private String motif;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
