package backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.entity.ProductData;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductData, Integer> {
    ProductData findByProductId(int productId);
    List<ProductData> findByCategory(String category);
    
    @Query("SELECT p FROM ProductData p WHERE p.cart >= 1")
    List<ProductData> findByCartGreaterThanOne();
}
