package fr.askhim.api.repository;

import fr.askhim.api.models.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type getTypeByLibelle(String libelle);

}
