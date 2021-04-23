package liv.pack.sn.controlleur;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.Deflater;
import java.io.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity.BodyBuilder;
import javax.servlet.ServletContext;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import liv.pack.sn.exceptions.ResourceNotFoundException;
import liv.pack.sn.modeles.JoinLivreur_Trajet;
import liv.pack.sn.modeles.Livreur;
import liv.pack.sn.modeles.Livreur_Trajet;
import liv.pack.sn.modeles.Photo;
import liv.pack.sn.modeles.Trajet;
import liv.pack.sn.modeles.User;
import liv.pack.sn.repos.LivTrajetRepos;
import liv.pack.sn.repos.LivreurRepos;
import liv.pack.sn.repos.TrajetRepos;
import liv.pack.sn.repos.UserRepos;
import liv.pack.sn.services.ServicesLivTrajet;
import liv.pack.sn.services.ServicesLivreur;
import liv.pack.sn.services.ServicesTrajet;
import liv.pack.sn.services.ServicesUser;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")

public class ControlleurPrincipal {
	@Autowired  ServletContext context;
	ServicesLivreur servicesLivreur;
    @Autowired
    private LivreurRepos  livreurRepos;
    private TrajetRepos  trajetRepos;
   // private LivTrajetRepos  livTrajetRepos;
    private UserRepos repository;
   
   //  ServicesLivTrajet livTrajet;
	ServicesTrajet servicesTrajet;
	ServicesUser servicesUser;
	//@GetMapping(value= {"/","/getrvs"})
	
	@GetMapping("/")
	public String index() {
		return "Bienvenue sur mon back-end";
	}
	public ControlleurPrincipal(ServicesTrajet servicesTrajet,ServicesLivreur servicesLivreur,ServicesUser servicesUser) {
		 this.servicesTrajet=servicesTrajet;
		 this.servicesLivreur=servicesLivreur;
		 // this.livTrajet=livTrajet;
		  this.servicesUser=servicesUser;
	}
	
@PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
public Livreur upload( @RequestParam("file") MultipartFile file,@RequestParam("livreur") String livreur) {

                Livreur userJson = servicesLivreur.getJson(livreur, file);
                return userJson;
}
	
@PostMapping(value="/sonuna",produces= "application/json")
public ResponseEntity<Livreur> uplaodImage(@RequestParam("imageFile") MultipartFile file,@RequestParam("nom") String nom,
		   @RequestParam("prenom") String prenom,@RequestParam("numTel") String numTel,@RequestParam("adresse") String adresse,@RequestParam("email") String email,
		   @RequestParam("moyen_transport") String moyen_transport) throws IOException {


       System.out.println("Original Image Byte Size - " + file.getBytes().length);
       Livreur rv=new Livreur();
       rv.setPhoto(file.getOriginalFilename());
       rv.setPicByte(compressBytes(file.getBytes()));
       rv.setType(file.getContentType());
      rv.setNom(nom);
      rv.setPrenom(prenom);
      rv.setAdresse(adresse);
      rv.setEmail(email);
      rv.setNumTel(numTel);
      rv.setMoyen_transport(moyen_transport);

      livreurRepos.save(rv);
       return new ResponseEntity<Livreur>(rv,HttpStatus.OK);
   }
// compress the image bytes before storing it in the database

   public static byte[] compressBytes(byte[] data) {

       Deflater deflater = new Deflater();

       deflater.setInput(data);

       deflater.finish();

       ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
       byte[] buffer = new byte[1024];

       while (!deflater.finished()) {
           int count = deflater.deflate(buffer);

           outputStream.write(buffer, 0, count);
       }
       try {

           outputStream.close();
       } catch (IOException e) {

       }
       System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

       return outputStream.toByteArray();
   }
	
   public static byte[] decompressBytes(byte[] data) {
	           Inflater inflater = new Inflater();
	           inflater.setInput(data);
	           ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	  
	           byte[] buffer = new byte[1024];
	
	           try {

	               while (!inflater.finished()) {
	   
	                   int count = inflater.inflate(buffer);
	                   outputStream.write(buffer, 0, count);
	               }
	               outputStream.close();
	  
	           } catch (IOException ioe) {
	           } catch (DataFormatException e) {
	           }
	           return outputStream.toByteArray();
	       }
	   
	   
	


	
	/*@GetMapping(value = "/Trajet/{source}/{destination}")
    public ResponseEntity<List<Trajet>>trajetLivreur(@PathVariable("source") String source,@PathVariable("destination") String destination) {
		List<Trajet> rvs=trajetRepos.findBySourceAndDestination(source,destination);
		return new ResponseEntity<List<Trajet>>(rvs,HttpStatus.OK);
    }*/
	 @GetMapping(path = { "/getImage/{id}" })
	     public Photo getImage(@PathVariable("id") Long id) throws Exception {

	         Livreur retrievedImage = livreurRepos.findById(id).get();
	         Photo img = new Photo(retrievedImage.getPhoto(), retrievedImage.getType(),
	                 decompressBytes(retrievedImage.getPicByte()));
	         return img;
	     }

	@GetMapping("/chercherLivreur/{id}")
	public ResponseEntity<Livreur>chercherRV(@PathVariable("id") Long id){
		Livreur rvs=servicesLivreur.trouverLivreurById(id);
		return new ResponseEntity<Livreur>(rvs,HttpStatus.OK);
	}
	@DeleteMapping("/supprimerLivreur/{id}")
	public void supprimer(@PathVariable("id") Long id){
		//livTrajet.supprimerByIdLivreurTraj(id);
		
		 /*Livreur rv= servicesLivreur.trouverLivreurById(id);
		 
		 rv.getTrajet().add(tr);
		rv.setTrajet(rv.getTrajet());*/
		
		servicesLivreur.supprimerByIdLivreur(id);
		
	}
	
	@PutMapping("/updateLivreur")
	public ResponseEntity<Livreur> updateLivreur(@RequestParam("idlivreur")Long id,@RequestParam("imageFile") MultipartFile file,@RequestParam("nom") String nom,
			   @RequestParam("prenom") String prenom,@RequestParam("numTel") String numTel,@RequestParam("adresse") String adresse,@RequestParam("email") String email,
			   @RequestParam("moyen_transport") String moyen_transport) throws IOException {


	       System.out.println("Original Image Byte Size - " + file.getBytes().length);
	       Livreur rv=servicesLivreur.trouverLivreurById(id);;
	       rv.setPhoto(file.getOriginalFilename());
	       rv.setPicByte(compressBytes(file.getBytes()));
	       rv.setType(file.getContentType());
	      rv.setNom(nom);
	      rv.setPrenom(prenom);
	      rv.setAdresse(adresse);
	      rv.setEmail(email);
	      rv.setNumTel(numTel);
	      rv.setMoyen_transport(moyen_transport);

	      Livreur newRv=servicesLivreur.updateLivreur(rv);
	       return new ResponseEntity<Livreur>(rv,HttpStatus.OK);
	   }
	
	
	@PutMapping("/updateLivreurOrig")
	public ResponseEntity<Livreur>update(@RequestBody Livreur rv){
		Livreur newRv=servicesLivreur.updateLivreur(rv);
              return new ResponseEntity<Livreur>(rv,HttpStatus.OK);
	}
	@PutMapping("/noterLivreur")
	public ResponseEntity<Livreur>updateLivreur(@RequestParam("idlivreur")Long id,@RequestParam("note") String note){
		Livreur rv=servicesLivreur.trouverLivreurById(id);
		             rv.setNote(note);
		             
		             Livreur newRv=servicesLivreur.updateLivreur(rv);
              return new ResponseEntity<Livreur>(rv,HttpStatus.OK);
	}
	@PutMapping("/attribuerTrajetLivreur")
	public ResponseEntity<Livreur>attribuerTrajetLivreur(@RequestParam("idLivreur") Long id1,@RequestParam("idTrajet") Long id2){
		 Livreur rv= servicesLivreur.trouverLivreurById(id1);
		 Trajet tr=  servicesTrajet.trouvertrajetById(id2);
		 Set<Trajet> trajet=new HashSet<>();
		 rv.getTrajet().add(tr);
		rv.setTrajet(rv.getTrajet());
		 /*tr.getLivreur().add(rv);
			tr.setLivreur(tr.getLivreur());         
		             Trajet newTr=servicesTrajet.updateTrajet(tr) ;*/
		             Livreur newRv=servicesLivreur.updateLivreur(rv);
              return new ResponseEntity<Livreur>(rv,HttpStatus.OK);
	}
	@PutMapping("/attribuerLivreurTrajet")
	public void attribuerLivreurTrajet(@RequestParam("idLivreur") Long id1,@RequestParam("idTrajet") Long id2){
		Livreur rv= servicesLivreur.trouverLivreurById(id1);
		 Trajet tr=  servicesTrajet.trouvertrajetById(id2);
		 Set<Trajet> trajet=new HashSet<>();
		 rv.getTrajet().add(tr);
		rv.setTrajet(rv.getTrajet());
		         
		             Trajet newTr=servicesTrajet.updateTrajet(tr) ;
		             Livreur newRv=servicesLivreur.updateLivreur(rv);
             
	}
	

	
	
	 
	@GetMapping("/getTrajet")
	public ResponseEntity<List<Trajet>>getAllTrajet(){
		List<Trajet> rvs=servicesTrajet.getAllTrajet();
		/*List<Trajet>rvs2 =new ArrayList<Trajet>();
		int i;
		for (i=0;i<rvs.size();i++) {
			Trajet trajet=new Trajet(rvs.get(i).getSource(),rvs.get(i).getDestination());
			trajet.setId(rvs.get(i).getId());
			trajet.setLivreur(rvs.get(i).getLivreur());
			
				for (Livreur monElement : trajet.getLivreur()) {
					 Livreur livreur = new Livreur(monElement.getNom(), monElement.getPrenom(), monElement.getNumTel(), monElement.getAdresse(), monElement.getEmail(), monElement.getPhoto(),
							 monElement.getMoyen_transport());
					 livreur.setNote(monElement.getNote());
					 livreur.setId(monElement.getId());
					 livreur.setType(monElement.getType());
					 livreur.setPicByte(decompressBytes(monElement.getPicByte()));
					trajet.getLivreur().add(livreur);
				}
		
			rvs2.add(trajet);
	
		}*/
		
		return new ResponseEntity<List<Trajet>>(rvs,HttpStatus.OK);
	}
	 
	@GetMapping("/getLivreur")
	public ResponseEntity<List<Livreur>>getAllLivreur(){
		List<Livreur> rvs=servicesLivreur.getAllLivreur();
		List<Livreur>rvs2 =new ArrayList<Livreur>();
		int i;
		for (i=0;i<rvs.size();i++) {
			
			 Livreur livreur = new Livreur(rvs.get(i).getNom(), rvs.get(i).getPrenom(), rvs.get(i).getNumTel(), rvs.get(i).getAdresse(), rvs.get(i).getEmail(), rvs.get(i).getPhoto(),
					 rvs.get(i).getMoyen_transport());
			 livreur.setNote(rvs.get(i).getNote());
			 livreur.setId(rvs.get(i).getId());
			 livreur.setType(rvs.get(i).getType());
			 livreur.setPicByte(decompressBytes(rvs.get(i).getPicByte()));
				for (Trajet monElement : rvs.get(i).getTrajet()) {
					livreur.getTrajet().add(monElement);
				}
		
			rvs2.add(livreur);
	
		}
		return new ResponseEntity<List<Livreur>>(rvs2,HttpStatus.OK);
	}
	
	@PostMapping("/ajouterTrajet")
	public ResponseEntity<Trajet>ajouterRV(@RequestBody Trajet rv){
		Trajet newRv=servicesTrajet. AjouterTrajet(rv);
              return new ResponseEntity<Trajet>(rv,HttpStatus.OK);
	}
	
	/*@GetMapping("/chercherTrajet/{id}")
	public ResponseEntity<Trajet>chercherTrajet(@PathVariable("id") Long id){
		Trajet rvs=servicesTrajet.trouvertrajetById(id);
		return new ResponseEntity<Trajet>(rvs,HttpStatus.OK);
	}*/
	
	@PutMapping("/updateTrajet")
	public ResponseEntity<Trajet>update(@RequestBody Trajet rv){
		Trajet newRv=servicesTrajet.updateTrajet(rv);
              return new ResponseEntity<Trajet>(rv,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	@GetMapping("/users/5/{login}")
	  public   ResponseEntity<User> getUtilisateurByLogin(@PathVariable("login") String login){
		  User Utilisateur = servicesUser.trouverUserByEmail(login);
				 
		   return new ResponseEntity<User>(Utilisateur,HttpStatus.OK);
	  } 
	
	@PostMapping("/users")
	public  ResponseEntity<User> createUtilisateur( @RequestBody User Utilisateur) {
		
		User newRv=servicesUser. AjouterUser(Utilisateur);
	        // repository.save(Utilisateur);
	         return new ResponseEntity<User>(Utilisateur,HttpStatus.OK);
	}
	
	@GetMapping("/getUtilisateurs")
	public ResponseEntity<List<User>>getUsers(){
		List<User> rvs=servicesUser.getAllUser();
		return new ResponseEntity<List<User>>(rvs,HttpStatus.OK);
	}
	@DeleteMapping("/supprimerUser/{id}")
	public void supprimerUser(@PathVariable("id") Long id){
		//livTrajet.supprimerByIdLivreurTraj(id);
		
		 /*Livreur rv= servicesLivreur.trouverLivreurById(id);
		 
		 rv.getTrajet().add(tr);
		rv.setTrajet(rv.getTrajet());*/
		
		servicesUser.supprimerByIdUser(id);
		
	}
	@DeleteMapping("/supprimerTrajet/{id}")
	public void supprimerTrajet(@PathVariable("id") Long id){
		servicesTrajet.supprimerByIdTrajet(id);
		
	}
	
	
	
	

}
