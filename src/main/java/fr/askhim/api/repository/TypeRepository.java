package fr.askhim.api.repository;

import fr.askhim.api.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByLibelle(String type);
    Type getTypeByLibelle(String libelle);
}
