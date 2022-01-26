package fr.askhim.api.repository;

import fr.askhim.api.entity.typeService.TacheMenageres.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Long> {

    Materiel getMaterielByLibelle(String libelle);
}
