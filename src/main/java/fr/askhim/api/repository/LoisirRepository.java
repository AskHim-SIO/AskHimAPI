package fr.askhim.api.repository;

import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.typeService.Loisir.Loisir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoisirRepository extends JpaRepository<Loisir, Long> {

    Loisir findByService(Service service);

}
