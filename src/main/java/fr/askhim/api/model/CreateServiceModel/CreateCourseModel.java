package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCourseModel extends CreateServiceModel {

    private String accompagnement;
    private String listeCourse;
    private String typeLieu;

}
