package liv.pack.sn.modeles;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Trajet {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false, updatable=false)
	private String source;
	private String destination;
	@OneToMany(cascade = CascadeType.ALL)
	Set<Livreur> livreur=new HashSet<>();
	
	

	
	
	
	public Trajet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Trajet(String source, String destination) {
		super();
		this.source = source;
		this.destination = destination;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	
	
}
