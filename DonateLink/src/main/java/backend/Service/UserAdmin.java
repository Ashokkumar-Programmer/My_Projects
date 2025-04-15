package backend.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "useradmin")
public class UserAdmin {
	
	@Id
	@Column(nullable=true, length=50)
	String username;
	
	
	@Column(nullable=true, length=100)
	String admin_fullname;
	
	@Column(nullable=true, length=300)
	String admin_address;
	
	@Column(nullable=true, length=100)
	String admin_email;
	
	@Column(nullable=true, length=10)
	Long admin_phonenumber;

	public String getAdmin_fullname() {
		return admin_fullname;
	}

	public void setAdmin_fullname(String admin_fullname) {
		this.admin_fullname = admin_fullname;
	}

	public String getAdmin_address() {
		return admin_address;
	}

	public void setAdmin_address(String admin_address) {
		this.admin_address = admin_address;
	}

	public String getAdmin_email() {
		return admin_email;
	}

	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}

	public Long getAdmin_phonenumber() {
		return admin_phonenumber;
	}

	public void setAdmin_phonenumber(Long admin_phonenumber) {
		this.admin_phonenumber = admin_phonenumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
