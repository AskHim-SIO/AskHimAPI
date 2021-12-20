package fr.askhim.api.repository;

import fr.askhim.api.models.entity.AskHimUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AskHimUserEntity, Long> {

}
