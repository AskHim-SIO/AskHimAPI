package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.TacheMenageres.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterielRepository extends JpaRepository<Materiel, Long> {

    Materiel getMaterielByLibelle(String libelle);
}
