package fr.askhim.api.repository;

import fr.askhim.api.entity.Energie;
import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.services.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergieRepository extends JpaRepository<Energie, Long> {
}
