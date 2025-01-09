package backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.UserCustomer;

public interface UserCustomerRepo extends JpaRepository<UserCustomer, String> {
	UserCustomer findByUsername(String username);
}
