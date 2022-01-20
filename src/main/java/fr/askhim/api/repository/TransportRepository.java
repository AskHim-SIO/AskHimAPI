package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<Transport, Long> {
}
