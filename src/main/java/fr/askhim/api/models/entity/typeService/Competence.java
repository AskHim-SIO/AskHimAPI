package fr.askhim.api.models.entity.typeService;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Competence")
public class Competence {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    // un motif pour des transports
    @OneToMany(mappedBy = "formation")
    Set<Formation> formations;
}
