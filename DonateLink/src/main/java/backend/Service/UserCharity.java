package backend.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usercharity")
public class UserCharity {
	@Id
	@Column(nullable=true, length=50)
	String username;
	
	@Column(nullable=true, length=500)
	String charityname;
	
	@Column(nullable=true, length=100)
	String inchargename;
	
	@Column(nullable=true, length=300)
	String address;
	
	@Column(nullable=true)
	Long strength;
	
	@Column(nullable=true, length=100)
	String charitytype;
	
	@Column(nullable=true, length=300)
	String email;
	
	@Column(nullable=true, length=10)
	Long phonenumber;
	
	@Column(nullable=true)
	String approval;

	public String getCharityname() {
		return charityname;
	}

	public void setCharityname(String charityname) {
		this.charityname = charityname;
	}

	public String getInchargename() {
		return inchargename;
	}

	public void setInchargename(String inchargename) {
		this.inchargename = inchargename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getStrength() {
		return strength;
	}

	public void setStrength(Long strength) {
		this.strength = strength;
	}

	public String getCharitytype() {
		return charitytype;
	}

	public void setCharitytype(String charitytype) {
		this.charitytype = charitytype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(Long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
