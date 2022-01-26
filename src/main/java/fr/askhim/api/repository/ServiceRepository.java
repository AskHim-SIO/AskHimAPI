package fr.askhim.api.repository;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByType(Type type);
    List<Service> findTop20ByOrderByPostDateDesc();
}
