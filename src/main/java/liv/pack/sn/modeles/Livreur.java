package liv.pack.sn.modeles;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.web.multipart.MultipartFile;
@Entity
public class Livreur {
@Id
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
@Column(nullable=false, updatable=false)
private String nom;
private String prenom;
private String numTel;
private String adresse;
private String email;
private String moyen_transport;
private String note;
@Column(name = "photo")
private String photo;
@Column(name = "type")
private String type;
@Column(name = "picByte" ,length = 696362)
    private byte[] picByte;

@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER )
Set<Trajet> trajet=new HashSet<>(); 




	public String getNote() {
	return note;
}







public void setNote(String note) {
	this.note = note;
}







	public String getType() {
	return type;
}







public void setType(String type) {
	this.type = type;
}







public byte[] getPicByte() {
	return picByte;
}







public void setPicByte(byte[] picByte) {
	this.picByte = picByte;
}







	public Livreur(String nom, String prenom, String numTel, String adresse, String email, String photo,
		String moyen_transport) {
	super();
	this.nom = nom;
	this.prenom = prenom;
	this.numTel = numTel;
	this.adresse = adresse;
	this.email = email;
	this.photo = photo;
	this.moyen_transport = moyen_transport;
}







	public String getMoyen_transport() {
	return moyen_transport;
}







public void setMoyen_transport(String moyen_transport) {
	this.moyen_transport = moyen_transport;
}



	
    
	



	
	public Livreur() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	



	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Set<Trajet> getTrajet() {
		return trajet;
	}




	public void setTrajet(Set<Trajet> trajet) {
		this.trajet = trajet;
	}




	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNumTel() {
		return numTel;
	}
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	


	public String getPhoto() {
		return photo;
	}



	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
    
}
