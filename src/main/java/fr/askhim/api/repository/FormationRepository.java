package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.Formation.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation, Long> {
}
