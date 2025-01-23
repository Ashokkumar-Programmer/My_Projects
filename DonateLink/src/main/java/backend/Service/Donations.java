package backend.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="donations")
public class Donations {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    int id;

	@Column(nullable=true, length=50)
	String username;
	
	@Column(nullable=true, length=200, name="donation_item")
	String donationItem;
	
	@Column(nullable=true, length=100)
	Long donation_target;
	
	@Column(nullable=true, length=100)
	Long donation_raised;
	
	@Column(nullable=true, length=100)
	String donation_category;
	
	@Column(nullable=true, length=100)
	Boolean reached;

	@Column(nullable=true, length=300)
	String image_path;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDonationItem() {
		return donationItem;
	}

	public void setDonationItem(String donationItem) {
		this.donationItem = donationItem;
	}

	public Long getDonation_target() {
		return donation_target;
	}

	public void setDonation_target(Long donation_target) {
		this.donation_target = donation_target;
	}

	public Long getDonation_raised() {
		return donation_raised;
	}

	public void setDonation_raised(Long donation_raised) {
		this.donation_raised = donation_raised;
	}

	public String getDonation_category() {
		return donation_category;
	}

	public void setDonation_category(String donation_category) {
		this.donation_category = donation_category;
	}

	public Boolean getReached() {
		return reached;
	}

	public void setReached(Boolean reached) {
		this.reached = reached;
	}
}
