package fr.askhim.api.repository;

import fr.askhim.api.entity.typeService.Loisir.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JeuRepository extends JpaRepository<Jeu, Long> {

    Jeu findByLibelle(String libelle);

}
