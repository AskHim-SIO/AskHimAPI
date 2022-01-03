package fr.askhim.api.models.entity.typeService;

import fr.askhim.api.models.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Transport")
public class Transport extends Service {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String pointDepart;

    @Column(nullable = false)
    private String pointArriver;

    @Column(nullable = false)
    private int nbPlaceDispo;

    @Column(nullable = false)
    private String vehiculePerso;

    @ManyToOne
    @JoinColumn(name="motif_id", nullable=false)
    private Motif motif;
}