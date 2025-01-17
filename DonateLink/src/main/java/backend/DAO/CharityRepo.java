package backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Service.Donations;

public interface CharityRepo extends JpaRepository<Donations, Integer> {
	Donations findByUsername(String username);
	Donations findByUsernameAndDonationItem(String username, String donationItem);
}
