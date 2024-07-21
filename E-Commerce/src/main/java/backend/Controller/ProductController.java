package backend.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.dao.ProductRepository;
import backend.dao.UserRespository;
import backend.entity.ProductData;
import backend.entity.UserData;

@Controller
public class ProductController {
	
	String username;
	
    @Autowired
    ProductRepository repo;
    
    @Autowired
    UserRespository userrepo;
    
    @GetMapping("/addproduct")
    public String addproduct(Model model) {
        model.addAttribute("product", new ProductData());
        return "AddProduct";
    }
    
    @GetMapping("/buy")
    public String buy() {
    	return "buynow";
    }
    
    @PostMapping("/productadded")
    public String productadded(ProductData product) {
        repo.save(product);
        return "redirect:/allproducts";
    }
    
    @GetMapping("/notfound")
    public String notfound() {
    	return "notfound";
    }
    
    @GetMapping("/allproduct")
    public String viewAllProduct(@RequestParam String category,@RequestParam String name ,HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        username = null;
        String password = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

        if (username == null || password == null) {
            return "403";
        }
        
        List<ProductData> products = repo.findByCategory(category);
        List<ProductData> cartProducts = repo.findByCartGreaterThanOne();
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("product", products);
        model.addAttribute("category", category);
        model.addAttribute("name", name);
        return "products";
    }
    
    @PostMapping("/addcart")
    public String addcart(@RequestParam String category,@RequestParam String name ,@RequestParam int productid, Model model) {
	   ProductData product = repo.findById(productid).orElse(null);
	   product.setCart(product.getCart()+1);
	   repo.save(product);
	   return "redirect:/allproduct?category="+category+"&name="+name;
    }
    
    @GetMapping("/cartPage")
    public String cartPage(HttpServletRequest request,Model model) {
    	Cookie[] cookies = request.getCookies();
        username = null;
        String password = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }
        
        if (username == null || password == null) {
            return "403";
        }
    	List<ProductData> cartProducts = repo.findByCartGreaterThanOne();
        model.addAttribute("cartProducts", cartProducts);
        UserData user = userrepo.findByUsername(username);
        model.addAttribute("address", user.getAddress());
        return "AddToCart";
    }
    
    @PostMapping("/deletecart")
    public String deletecart(@RequestParam int productid, Model model) {
       ProductData product = repo.findById(productid).orElse(null);
       product.setCart(product.getCart()-1);
       repo.save(product);
       return "redirect:/cartPage";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam String category, @RequestParam String name, @RequestParam String username,Model model) {
        List<ProductData> products = repo.findAll();
        List<ProductData> result = products.stream()
            .filter(product -> Arrays.stream(product.getProductname().split(" "))
                .anyMatch(word -> word.equalsIgnoreCase(name)))
            .collect(Collectors.toList());
        model.addAttribute("product", result);
        if (!products.isEmpty()) {
            model.addAttribute("category", products.get(0).getCategory());
        }
        model.addAttribute("name", username);
        List<ProductData> cartProducts = repo.findByCartGreaterThanOne();
        model.addAttribute("cartProducts", cartProducts);
        return "searchResults";
    }
    
    @GetMapping("/productdetails")
    public String productdetails(Model model) {
    	List<ProductData> product = repo.findAll();
    	model.addAttribute("product", product);
    	return "productdetails";
    }
    
    @PostMapping("/updateSolded")
    public String updateSolded() {
        List<ProductData> cartProducts = repo.findByCartGreaterThanOne();
        for (ProductData product : cartProducts) {
            product.setSolded(product.getSolded() + 1);
            repo.save(product);
        }
        return "redirect:/buy";
    }
    
    @GetMapping("/deleteproduct")
	public String deleteuser(@RequestParam("productid") int productid) {
		repo.deleteById(productid);
		return "redirect:/productdetails";
	}
}

