package fr.askhim.api.repository;

import fr.askhim.api.models.entity.Photo;
import fr.askhim.api.models.entity.Service;
import fr.askhim.api.models.entity.Type;
import fr.askhim.api.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByLibelle(String type);
    Type getTypeByLibelle(String libelle);
}
