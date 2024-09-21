package backend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.dao.ProductRepository;
import backend.dao.UserRespository;
import backend.entity.ProductData;
import backend.entity.UserData;
import backend.service.TextToSpeechService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	@Autowired
	private UserRespository repo;
	
	@Autowired
	private TextToSpeechService textToSpeechService;
	
	@Autowired
	private ProductRepository repo1;
	
	@PostMapping("/speak")
	@ResponseBody
	public ResponseEntity<String> speak(@RequestBody String text) {
	    try {
	    	String uservoice = text.replace("text", "");
	        textToSpeechService.speak(uservoice);
	        return ResponseEntity.ok("Text successfully spoken");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to speak text: " + e.getMessage());
	    }
	}
	
	@GetMapping("/adduser")
	public String adduser(Model model) {
		model.addAttribute("user", new UserData());
		return "signup";
	}
	
	@PostMapping("/useradded")
    public String processRegistration(UserData user) {
        repo.save(user);
        return "login";
    }
	
	@GetMapping("/loginPage")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletResponse response, HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("username".equals(cookie.getName()) || "password".equals(cookie.getName())) {
	                cookie.setValue(null);
	                cookie.setMaxAge(0);
	                response.addCookie(cookie);
	            }
	        }
	    }
	    return "redirect:/loginPage";
	}
	
	@PostMapping("/authenticate")
    public String login(@RequestParam String username, 
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        UserData user = repo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
        	Cookie usernameCookie = new Cookie("username", username);
            Cookie passwordCookie = new Cookie("password", password);
            usernameCookie.setMaxAge(5 * 24 * 60 * 60);
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        	if(user.getUsertype().equals("customer")) {
        		return "redirect:/shopsmart?username="+user.getUsername();
        	}else if(user.getUsertype().equals("seller")) {
        		return "redirect:/addproduct";
        	}else if(user.getUsertype().equals("admin")) {
        		return "redirect:/adminPage";
        	}
        }
        model.addAttribute("error", "Invalid username or password");
        return "403";
    }
	
	@GetMapping("/forget")
	public String forget() {
		return "ForgetPassword";
	}
	
	@GetMapping("/adminPage")
	public String adminPage(Model model) {
		List<UserData> user = repo.findAll();
		List<ProductData> product = repo1.findAll();
		double totalSum = product.stream().mapToDouble(p -> p.getProductprice() * p.getSolded()).sum();
		model.addAttribute("user", user);
		model.addAttribute("product", product);
		model.addAttribute("total", totalSum);
		return "admin";
	}
	
	@GetMapping("/deleteuser")
	public String deleteuser(@RequestParam("username") String username) {
		repo.deleteById(username);
		return "redirect:/adminPage";
	}
}
