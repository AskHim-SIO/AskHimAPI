package fr.askhim.api.repository;

import fr.askhim.api.entity.Photo;
import fr.askhim.api.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByService(Service service);
}
