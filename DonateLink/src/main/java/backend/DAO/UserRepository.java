package backend.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.UserLogin;

public interface UserRepository extends JpaRepository<UserLogin, String> {
	UserLogin findByUsername(String username);
	
	List<UserLogin> findByUsertype(String usertype);
}
