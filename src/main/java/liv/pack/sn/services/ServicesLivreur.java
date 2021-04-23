package liv.pack.sn.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import liv.pack.sn.exceptions.LivreurException;
import liv.pack.sn.modeles.Livreur;
import liv.pack.sn.repos.LivreurRepos;
import liv.pack.sn.repos.TrajetRepos;
	@Service
	public class ServicesLivreur {
		private LivreurRepos  livreurRepos;
		private TrajetRepos trajetRepos;
		
	    @Autowired
		public ServicesLivreur(LivreurRepos rvrepo) {
			// TODO Auto-generated constructor stub
	    	livreurRepos=rvrepo;
		}
	    public Livreur getJson(String livreur, MultipartFile file) {

			Livreur userJson = new Livreur();
			
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				userJson = objectMapper.readValue(livreur, Livreur.class);
			} catch (IOException err) {
				System.out.printf("Error", err.toString());
			}
			
			
			return userJson;

		}
	    
		public Livreur AjouterLivreur(Livreur rv) {
			//rv.setCodeRV(UUID.randomUUID().toString());
			
			return livreurRepos.save(rv);
		}
		public List<Livreur>getAllLivreur() {
			
			return livreurRepos.findAll();
		}
       
		
	  public void supprimerByIdLivreur(Long id) {
		  livreurRepos.deleteById(id);
			
		}
	  public Livreur trouverLivreurById(Long id) {
			return livreurRepos.findById(id).orElseThrow(()->new LivreurException("Un Livreur avec l'id"+id+"n'existe pas"));
				
		}
	  public  Livreur updateLivreur( Livreur rv) {
			return livreurRepos.save(rv);
				
		}
	  
	}

