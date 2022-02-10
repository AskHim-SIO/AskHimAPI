package fr.askhim.api.services;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.services.Course;
import fr.askhim.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course getCourseByService(Service service){
        return courseRepository.findByService(service);
    }

}
