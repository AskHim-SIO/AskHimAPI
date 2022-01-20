package fr.askhim.api.models.entity.typeService.Transport;

import fr.askhim.api.models.entity.typeService.Transport.Transport;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Motif")
public class Motif{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    // un motif pour des transports
    @OneToMany(mappedBy = "motif")
    Set<Transport> transport;
}
