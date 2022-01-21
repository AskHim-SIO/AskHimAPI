package fr.askhim.api.models.entity.typeService;

import fr.askhim.api.models.entity.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Course")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String accompagnement;

    @Column(nullable = false)
    private String typeLieu;

    @Column(nullable = false)
    private int adresseLieu;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
