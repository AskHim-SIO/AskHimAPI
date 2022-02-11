package fr.askhim.api.entity.services;

import fr.askhim.api.entity.Service;
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
    private String listeCourse;

    @Column(nullable = false)
    private String typeLieu;

    @OneToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;
}
