package fr.askhim.api.repository;

import fr.askhim.api.entity.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LieuRepository extends JpaRepository<Lieu, Long> {

    Lieu findByVille(String ville);
    Lieu findByAdresseAndCodePostalAndVille(String adresse, int codePostal, String ville);

}
