package fr.askhim.api.repository;

import fr.askhim.api.entity.typeService.Formation.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

    Competence findByLibelle(String libelle);

}
