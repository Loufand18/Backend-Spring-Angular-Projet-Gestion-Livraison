package liv.pack.sn.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import liv.pack.sn.modeles.Livreur;
import liv.pack.sn.modeles.User;

public interface UserRepos extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
