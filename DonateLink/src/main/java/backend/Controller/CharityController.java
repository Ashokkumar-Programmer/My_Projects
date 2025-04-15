package backend.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.DAO.CharityRepo;
import backend.Service.Donations;

@Controller
public class CharityController {
	
	@Autowired
	CharityRepo charityrepo;
	
	@GetMapping("/DonateLink_createdonation")
	String CharityLink_createdonation(Model model, @RequestParam("username") String username) {
		model.addAttribute("username1", username);
		model.addAttribute("donate", new Donations());
		return "Charity/createdonation";
	}
	
	@PostMapping("/pricedetails")
	@ResponseBody
	public Map<String, Object> pricedetails(@RequestBody Map<String, String> payload) {
	    String productname = payload.get("productname");
	    String category = payload.get("productcategory");

	    PriceController priceController = new PriceController();
	    int price = priceController.getproductprice(productname, category);

	    Map<String, Object> response = new HashMap<>();
	    response.put("price", price);
	    return response;
	}
	
	@PostMapping("/createdonations")
	String createdonations(Model model, Donations donate,@RequestParam("image_path") String image_path,@RequestParam("username") String username,@RequestParam("productname") String productname ,@RequestParam("target") Long target) {
		List<Donations> existingDonation = charityrepo.findByUsernameAndDonationItem(username, productname.toLowerCase());
		if(existingDonation.isEmpty()) {
			donate.setUsername(username);
			donate.setDonation_raised((long) 0);
			donate.setReached(false);
			donate.setDonation_target(target);
			donate.setDonationItem(productname.toLowerCase());
			donate.setImage_path(image_path);
			charityrepo.save(donate);
			return "redirect:/Donatelink_funding?username="+username+"&query=novalue";
		}
		else {
			model.addAttribute("error", true);
			model.addAttribute("username1", username);
			model.addAttribute("donate", new Donations());
			return "Charity/createdonation";
		}
	}
	
	@GetMapping("/Donatelink_funding")
	String Donatelink_funding(Model model, @RequestParam("username") String username, @RequestParam("query") String productname) {
		List<Donations> donate = null;
		if(productname.equals("novalue")) {
			donate = charityrepo.findByUsername(username);
		}
		else {
			donate = charityrepo.findByUsernameAndDonationItem(username, productname.toLowerCase());
		}
		if(donate.isEmpty()) {
			model.addAttribute("error", "No data found ");
		}
		model.addAttribute("donate", donate);
		model.addAttribute("username", username);
		return "Charity/program";
	}
	
	@PostMapping("/deletedonation")
	public String postMethodName(@RequestParam("id") int id, @RequestParam("username") String username) {
		charityrepo.deleteById(id);
		return "redirect:/Donatelink_funding?username="+username+"&query=novalue";
	}
	

	
}
