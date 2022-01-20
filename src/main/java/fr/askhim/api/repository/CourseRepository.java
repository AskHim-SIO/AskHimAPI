package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
