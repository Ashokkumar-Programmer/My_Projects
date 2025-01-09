package backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.UserAdmin;

public interface UserAdminRepo extends JpaRepository<UserAdmin, String> {
	UserAdmin findByUsername(String username);
}
