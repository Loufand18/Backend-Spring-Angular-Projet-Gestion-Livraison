package liv.pack.sn.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import liv.pack.sn.modeles.Trajet;

public interface TrajetRepos  extends JpaRepository<Trajet, Long> {
	List<Trajet>findBySourceAndDestination(String source,String desti);
           
}
