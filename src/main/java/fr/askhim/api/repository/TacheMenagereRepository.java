package fr.askhim.api.repository;

import fr.askhim.api.entity.typeService.TacheMenageres.TacheMenagere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheMenagereRepository extends JpaRepository<TacheMenagere, Long> {
}
