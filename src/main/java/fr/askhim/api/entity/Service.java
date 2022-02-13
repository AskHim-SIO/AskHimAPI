package fr.askhim.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    @Column(columnDefinition = "boolean default 0")
    private Boolean state;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date postDate;

    @Column()
    @Temporal(TemporalType.DATE)
    private Date deleteDate;

    //mettre en favoris
    @ManyToMany(mappedBy = "favoris")
    Set<User> favorites_by;

    // Reponse d'un utilisateur
    @OneToMany(mappedBy = "service")
    Set<ResponseUserService> response;

    // un service a des photos
    @OneToMany(mappedBy = "service")
    private List<Photo> photos;

    //posté par?
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Un service appartient à un type
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    //Un service appartient à un lieu
    @ManyToOne
    @JoinColumn(name = "lieu_id", nullable = false)
    private Lieu lieu;
}
