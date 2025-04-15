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
import org.springframework.web.bind.annotation.RestController;

import backend.dao.ProductRepository;
import backend.dao.UserRespository;
import backend.entity.ProductData;
import backend.entity.UserData;

@Controller
public class TrialController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/trynow")
    public String tryNowPage(@RequestParam("productId") int productId, Model model) { 
        ProductData product = productRepository.findByProductId(productId);
        
        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID: " + productId);
        }
        
        model.addAttribute("product", product);
        return "trynow";
    }
    
}

