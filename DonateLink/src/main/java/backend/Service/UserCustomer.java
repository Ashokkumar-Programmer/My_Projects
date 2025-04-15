package backend.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usercustomer")
public class UserCustomer {
	
	@Id
	@Column(nullable=true, length=50)
	String username;
	
	@Column(nullable=true, length=100)
	String customer_fullname;
	
	@Column(nullable=true, length=300)
	String customer_address;
	
	@Column(nullable=true, length=100)
	String customer_email;
	
	@Column(nullable=true, length=10)
	Long customer_phonenumber;
	
	public String getCustomer_fullname() {
		return customer_fullname;
	}

	public void setCustomer_fullname(String customer_fullname) {
		this.customer_fullname = customer_fullname;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public Long getCustomer_phonenumber() {
		return customer_phonenumber;
	}

	public void setCustomer_phonenumber(Long customer_phonenumber) {
		this.customer_phonenumber = customer_phonenumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
