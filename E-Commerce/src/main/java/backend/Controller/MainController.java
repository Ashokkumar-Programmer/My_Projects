package backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.dao.ProductRepository;
import backend.dao.UserRespository;
import backend.entity.ProductData;
import backend.entity.UserData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {

	@Autowired
	private UserRespository repo;
	
	@Autowired
	private ProductRepository repo1;
	
	@GetMapping("/shopsmart")
	public String home(@RequestParam(value = "username", required = false) String username,HttpServletRequest request, Model model) {
		if (username == null || username.isEmpty()) {
	        return "redirect:/loginPage";
	    }
		
		UserData user = repo.findByUsername(username);
		Cookie[] cookies = request.getCookies();
		boolean username1 = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getValue().equals(null)) {
                	username1=true;
                }
            }
        }
        if (username1) {
            return "403";
        }
		model.addAttribute("name", user.getName());
		model.addAttribute("username", username);
		List<ProductData> cartProducts = repo1.findByCartGreaterThanOne();
        model.addAttribute("cartProducts", cartProducts);
		return "index";
	}
	
}
