package liv.pack.sn.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import liv.pack.sn.modeles.Livreur;
import liv.pack.sn.modeles.Trajet;

import java.util.List;
import java.util.Optional;


public interface LivreurRepos extends JpaRepository<Livreur, Long>{
	//@Query("SELECT * FROM Livreur l,Trajet p WHERE t. = ?1 AND t.bar = ?2")
    //List<Livreur> findByFooInAndBar(String fooIn, String bar);
	//List<Trajet>findBySourceAndDestination(String source,String desti);
	// @Query("SELECT n, a FROM livreur n JOIN n.trajet a WHERE n.source = :source AND n.destination = :destination")
	  //  public List<Object[]> TrajetLivreur(@Param("source") String source, @Param("destination") String destination);
}
