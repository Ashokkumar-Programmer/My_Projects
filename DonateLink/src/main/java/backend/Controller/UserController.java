package backend.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.DAO.CharityRepo;
import backend.DAO.UserAdminRepo;
import backend.DAO.UserCharityRepo;
import backend.DAO.UserCustomerRepo;
import backend.DAO.UserRepository;
import backend.Service.Donations;
import backend.Service.UserAdmin;
import backend.Service.UserCharity;
import backend.Service.UserCustomer;
import backend.Service.UserLogin;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	UserRepository userrepo;

	@Autowired
	UserAdminRepo adminrepo;

	@Autowired
	UserCustomerRepo customerrepo;

	@Autowired
	UserCharityRepo charityrepo;

	@Autowired
	CharityRepo charityrepo1;

	@GetMapping("/donatelink")
	String index() {
		return "redirect:/automatic_login";
	}

	@GetMapping("/donatelink_signup")
	String signup_page(Model model) {
		model.addAttribute("user", new UserLogin());
		return "Auth/signup";
	}

	@GetMapping("/donatelink_signin")
	String signin_page(Model model) {
		model.addAttribute("user", new UserLogin());
		return "Auth/signin";
	}

	@GetMapping("/donations")
	public String Donatelink_funding(Model model, @RequestParam("username") String username,
			@RequestParam("query") String productname,
			@RequestParam(value = "amount", required = false, defaultValue = "0") int amount) {

		List<Donations> donate = null;

		if (productname.equals("novalue")) {
			donate = charityrepo1.findAll();
		} else {
			donate = charityrepo1.findByUsernameAndDonationItem(username, productname.toLowerCase());
		}

		if (amount > 0) {
			donate = donate.stream().filter(donation -> donation.getDonation_target() <= amount)
					.collect(Collectors.toList());
		}

		if (donate.isEmpty()) {
			model.addAttribute("error", "No data found.");
		}

		model.addAttribute("donate", donate);
		model.addAttribute("username", username);

		return "Customer/donations";
	}

	@PostMapping("/updateTarget")
	public String updateTarget(Model model, @RequestParam("username") String username,
			@RequestParam("donationName") int donationName, @RequestParam("target") long target) {

		Optional<Donations> donate = charityrepo1.findById(donationName);
		Donations donations = donate.get();
		donations.setDonation_raised(target);
		charityrepo1.save(donations);
		return "redirect:/donations?username=" + username + "&query=novalue";
	}

	@GetMapping("/donatelink_forgetpassword")
	String forgetpassword() {
		return "Auth/forgetpassword1";
	}

	String otp = "", password = "", usertype = "";

	@PostMapping("/verifyuser")
	public String verifyUser(Model model, UserLogin user,
			@RequestParam(value = "username", required = false) String username) {
		user = userrepo.findByUsername(username);
		String email = "";

		if (user == null) {
			model.addAttribute("error", true);
			return "Auth/forgetpassword1";
		} else {
			usertype = user.getUsertype();
			password = user.getPassword();

			if (usertype.equals("customer")) {
				UserCustomer customer = customerrepo.findByUsername(username);
				email = customer.getCustomer_email();
			} else if (usertype.equals("charity")) {
				UserCharity charity = charityrepo.findByUsername(username);
				email = charity.getEmail();
			} else if (usertype.equals("admin")) {
				UserAdmin admin = adminrepo.findByUsername(username);
				email = admin.getAdmin_email();
			}
		}
		otp = new MailController().mailsent(email);
		return "Auth/forgetpassword2";
	}

	@PostMapping("/verifyotp")
	String verifyotp(@RequestParam(value = "otp", required = false) String getotp, Model model) {
		if (getotp != null && getotp.equals(otp)) {
			model.addAttribute("message", "Your password is " + password);
			model.addAttribute("link", true);
		} else {
			model.addAttribute("message", "Incorrect OTP");
		}
		return "Auth/forgetpassword2";
	}

	@GetMapping("/donatelink_charity")
	String charity_page(@RequestParam("username") String username, @RequestParam("query") String query, Model model) {

		List<Donations> donations = charityrepo1.findAll();

		List<UserLogin> users = userrepo.findAll();

		int donations_count = donations.size();
		int raised = 0;
		int required = 0;
		int charities = 0;
		for (Donations donate : donations) {
			raised += Integer.parseInt(String.valueOf(donate.getDonation_raised()));
			if (!donate.getDonation_target().equals(donate.getDonation_raised()))
				required += Integer.parseInt(String.valueOf(donate.getDonation_target()));
		}

		for (UserLogin user : users) {
			if (user.getUsertype().equals("charity"))
				charities += 1;
		}

		model.addAttribute("raised", String.valueOf(raised));
		model.addAttribute("donations_count", donations_count);
		model.addAttribute("required", String.valueOf(required));
		model.addAttribute("charities", charities);

		model.addAttribute("query", query);
		model.addAttribute("username", username);
		return "Charity/charity";
	}

	@GetMapping("/donatelink_customer")
	String customer_page(@RequestParam("username") String username, Model model) {

		List<Donations> donations = charityrepo1.findAll();

		List<UserLogin> users = userrepo.findAll();

		int donations_count = donations.size();
		int raised = 0;
		int required = 0;
		int charities = 0;
		for (Donations donate : donations) {
			raised += Integer.parseInt(String.valueOf(donate.getDonation_raised()));
			if (!donate.getDonation_target().equals(donate.getDonation_raised()))
				required += Integer.parseInt(String.valueOf(donate.getDonation_target()));
		}

		for (UserLogin user : users) {
			if (user.getUsertype().equals("charity"))
				charities += 1;
		}

		model.addAttribute("raised", String.valueOf(raised));
		model.addAttribute("donations_count", donations_count);
		model.addAttribute("required", String.valueOf(required));
		model.addAttribute("charities", charities);
		model.addAttribute("username", username);
		return "Customer/customer";
	}

	@GetMapping("/automatic_login")
	String automatic_login(@CookieValue(name = "donatelink_username", defaultValue = "default") String username,
			@CookieValue(name = "donatelink_password", defaultValue = "default") String password) {
		if (username.equals("default") && password.equals("default")) {
			return "redirect:/donatelink_signin";
		}
		UserLogin user = userrepo.findByUsername(username);
		if (user == null) {
			return "redirect:/donatelink_signin";
		} else if (user.getPassword().equals(password)) {
			if (user.getUsertype().equals("customer")) {
				return "redirect:/donatelink_customer";
			} else if (user.getUsertype().equals("charity")) {
				return "redirect:/donatelink_charity?username=" + username + "&query=" + "novalue";
			} else {
				return "redirect:/donatelink_admin?username=" + username;
			}
		} else {
			return "redirect:/donatelink_signin?username=" + username;
		}
	}

	@PostMapping("/user_signin")
	String user_signin(@RequestParam("checkboxvalue") boolean checkbox, UserLogin user1, Model model,
			HttpServletResponse response) {
		String username = user1.getUsername();
		String password = user1.getPassword();
		UserLogin user = userrepo.findByUsername(username);
		
		if (user == null) {
			model.addAttribute("error", "username is not found");
			model.addAttribute("user", new UserLogin());
			return "Auth/signin";
		} else if(user.getUsertype().equals("charity") && Boolean.FALSE.equals(user.getApproval())) {
			model.addAttribute("error", "Get approval from admin");
			model.addAttribute("user", new UserLogin());
			return "Auth/signin";
		}
		else if (user.getPassword().equals(password)) {
			UserCharity charity = charityrepo.findByUsername(username);
			if (charity != null) {
				if (charity.getApproval().equals("no")) {
					model.addAttribute("error", "Approval not grant by admin");
					model.addAttribute("user", new UserLogin());
					return "Auth/signin";
				}
			}
			if (checkbox) {
				new CookieController().setCookie(response, username, password);
			}
			if (user.getUsertype().equals("customer")) {
				return "redirect:/donatelink_customer?username=" + username;
			} else if (user.getUsertype().equals("charity")) {
				return "redirect:/donatelink_charity?username=" + username + "&query=" + "novalue";
			} else {
				return "redirect:/donatelink_admin?username=" + username;
			}
		} else {
			model.addAttribute("error", "Password is incorrect");
			model.addAttribute("user", new UserLogin());
			return "Auth/signin";
		}
	}

	@PostMapping("/user_register")
	String user_register(UserLogin user, @RequestParam("password") String password,
			@RequestParam("username") String username, @RequestParam("cpassword") String cpassword, Model model) {
		if (userrepo.findByUsername(username) != null) {
			model.addAttribute("usernameerror", true);
			model.addAttribute("user", new UserLogin());
			return "Auth/signup";
		} else if (password.equals(cpassword)) {
			if(user.getUsertype().equals("charity")) {
				user.setApproval(false);
			}else {
				user.setApproval(true);
			}
			userrepo.save(user);
			return "redirect:/user_details?usertype=" + user.getUsertype() + "&username=" + username;
		} else {
			model.addAttribute("passworderror", true);
			model.addAttribute("user", new UserLogin());
			return "redirect:/donatelink_signup";
		}
	}

	@GetMapping("/user_details")
	String user_details(@RequestParam("usertype") String usertype, @RequestParam("username") String username,
			Model model) {
		model.addAttribute("customer", new UserCustomer());
		model.addAttribute("charity", new UserCharity());
		model.addAttribute("admin", new UserAdmin());
		model.addAttribute("usertype", usertype);
		model.addAttribute("username", username);
		return "Auth/userdetails";
	}

	@PostMapping("/charity_register")
	String register_charity(@RequestParam("username") String username, UserCharity charity) {
		charity.setUsername(username);
		charity.setApproval("no");
		charityrepo.save(charity);
		return "redirect:/donatelink_signin";
	}

	@PostMapping("/customer_register")
	String register_customer(@RequestParam("username") String username, UserCustomer customer) {
		customer.setUsername(username);
		customerrepo.save(customer);
		return "redirect:/donatelink_signin";
	}

	@PostMapping("/admin_register")
	String register_admin(@RequestParam("username") String username, UserAdmin admin) {
		admin.setUsername(username);
		adminrepo.save(admin);
		return "redirect:/donatelink_signin";
	}

}