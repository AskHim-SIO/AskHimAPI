package fr.askhim.api.repository;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.services.TacheMenagere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheMenagereRepository extends JpaRepository<TacheMenagere, Long> {

    TacheMenagere findByService(Service service);

}
