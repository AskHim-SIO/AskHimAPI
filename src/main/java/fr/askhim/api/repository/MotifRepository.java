package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.Motif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotifRepository extends JpaRepository<Motif, Long> {

    Motif getMotifByLibelle(String libelle);

}
