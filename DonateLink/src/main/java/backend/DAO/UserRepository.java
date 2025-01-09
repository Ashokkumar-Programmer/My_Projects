package backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.UserLogin;

public interface UserRepository extends JpaRepository<UserLogin, String> {
	UserLogin findByUsername(String username);
}
