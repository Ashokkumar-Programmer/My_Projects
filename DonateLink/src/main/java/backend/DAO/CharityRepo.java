package backend.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.Donations;

public interface CharityRepo extends JpaRepository<Donations, Integer> {
	List<Donations> findByUsername(String username);
	List<Donations> findByUsernameAndDonationItem(String username, String donationItem);
	List<Donations> findByDonationItem(String donationItem);
}
