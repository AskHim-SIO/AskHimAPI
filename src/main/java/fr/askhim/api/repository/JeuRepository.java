package fr.askhim.api.repository;

import fr.askhim.api.models.entity.typeService.Loisir.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JeuRepository extends JpaRepository<Jeu, Long> {

    Jeu findByLibelle(String libelle);

}
