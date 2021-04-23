package liv.pack.sn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liv.pack.sn.exceptions.LivreurException;
import liv.pack.sn.modeles.Livreur;
import liv.pack.sn.modeles.Trajet;
import liv.pack.sn.repos.LivreurRepos;
import liv.pack.sn.repos.TrajetRepos;
@Service
public class ServicesTrajet {
	private TrajetRepos trajetRepos;
    @Autowired
	public ServicesTrajet(TrajetRepos rvrepo) {
		// TODO Auto-generated constructor stub
    	trajetRepos=rvrepo;
	}
	public Trajet AjouterTrajet(Trajet rv) {
		//rv.setCodeRV(UUID.randomUUID().toString());
		return trajetRepos.save(rv);
	}
	public List<Trajet>getAllTrajet() {
		
		return trajetRepos.findAll();
	}
	
  public void supprimerByIdTrajet(Long id) {
	  trajetRepos.deleteById(id);
		
	}
  public Trajet trouvertrajetById(Long id) {
		return trajetRepos.findById(id).orElseThrow(()->new LivreurException("Un Trajet avec l'id"+id+"n'existe pas"));
			
	}
  public Trajet updateTrajet( Trajet rv) {
		return trajetRepos.save(rv);
			
	}
	

}
