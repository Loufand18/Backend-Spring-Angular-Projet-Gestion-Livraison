package liv.pack.sn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liv.pack.sn.exceptions.LivreurException;
import liv.pack.sn.modeles.Trajet;
import liv.pack.sn.modeles.User;
import liv.pack.sn.repos.TrajetRepos;
import liv.pack.sn.repos.UserRepos;
@Service
public class ServicesUser {
	private UserRepos userRepos;
    @Autowired
    public ServicesUser(UserRepos rvrepo) {
		// TODO Auto-generated constructor stub
    	userRepos=rvrepo;
	}
public User AjouterUser ( User rv) {
	//rv.setCodeRV(UUID.randomUUID().toString());
	return userRepos.save(rv);
}
public List<User>getAllUser() {
	
	return userRepos.findAll();
}

public void supprimerByIdUser(Long id) {
	userRepos.deleteById(id);
	
}
public User trouverUserById(Long id) {
	return userRepos.findById(id).orElseThrow(()->new LivreurException("Un Trajet avec l'id"+id+"n'existe pas"));
		
}
public User trouverUserByEmail(String login) {
	return userRepos.findByEmail(login).orElseThrow(()->new LivreurException("Un Trajet avec l'id"+login+"n'existe pas"));
		
}
public User updateUser( User rv) {
	return userRepos.save(rv);
		
}


}
