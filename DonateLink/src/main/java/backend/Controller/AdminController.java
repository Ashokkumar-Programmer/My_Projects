package backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import backend.DAO.CharityRepo;
import backend.DAO.UserRepository;
import backend.Service.Donations;
import backend.Service.UserLogin;

@Controller
public class AdminController {

	@Autowired
	CharityRepo charityrepo;
	
	@Autowired
	UserRepository userrepo;
	
	@GetMapping("/donatelink_admin")
	public String adminPage(Model model) {
		
		List<Donations> donate = charityrepo.findAll();
		
		List<UserLogin> users = userrepo.findByUsertype("charity");
		
		model.addAttribute("donate", donate);
		model.addAttribute("charity", users);
		
		return "Admin/admin";
	}
	
}
