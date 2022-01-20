package fr.askhim.api.models.entity.typeService.Loisir;

import fr.askhim.api.models.entity.typeService.Loisir.Loisir;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Jeu")
public class Jeu {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    // un motif pour des transports
    @OneToMany(mappedBy = "jeu")
    Set<Loisir> loisirs;
}
