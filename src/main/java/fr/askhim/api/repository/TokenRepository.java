package fr.askhim.api.repository;

import fr.askhim.api.models.entity.Token;
import fr.askhim.api.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

}
