package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.TacheMenageres.TacheMenagere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheMenagereRepository extends JpaRepository<TacheMenagere, Long> {
}
