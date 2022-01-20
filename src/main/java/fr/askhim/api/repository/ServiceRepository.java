package fr.askhim.api.repository;

import fr.askhim.api.models.entity.Service;
import fr.askhim.api.models.entity.Type;
import fr.askhim.api.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    public List<Service> findByType(Optional<Type> type);
    public List<Service> findTop20ByOrderByPostDateDesc();
}
