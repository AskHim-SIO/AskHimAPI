package fr.askhim.api.repository;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.typeService.Formation.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    Formation findByService(Service service);
}
