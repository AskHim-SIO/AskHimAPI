package fr.askhim.api.entity.typeService.TacheMenageres;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Materiel")
public class Materiel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @ManyToMany
    @JoinTable(
            name = "Disposer_de",
            joinColumns = @JoinColumn(name = "materiel_id"),
            inverseJoinColumns = @JoinColumn(name = "tacheMenagere_id"))
    private List<TacheMenagere> tacheMenageres;
}
