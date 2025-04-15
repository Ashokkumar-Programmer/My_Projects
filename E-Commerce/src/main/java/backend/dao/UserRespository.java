package backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.entity.UserData;


public interface UserRespository extends JpaRepository<UserData, String> {
	UserData findByUsername(String username);
}
