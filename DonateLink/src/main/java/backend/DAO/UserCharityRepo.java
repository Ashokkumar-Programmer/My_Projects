package backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.UserCharity;

public interface UserCharityRepo extends JpaRepository<UserCharity, String> {
	UserCharity findByUsername(String username);
}
