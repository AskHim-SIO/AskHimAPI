package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.Loisir.Loisir;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoisirRepository extends JpaRepository<Loisir, Long> {
}
